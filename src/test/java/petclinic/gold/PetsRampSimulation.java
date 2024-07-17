package petclinic.gold;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import net.datafaker.Faker;

public class PetsRampSimulation extends Simulation {

    Faker faker = new Faker(new Locale("es"), new Random(42));

    int rampUsers = Integer.getInteger("users", 100);
    int rampDuration = Integer.getInteger("duration", 60);

    private final static String URL = System.getProperty("url", "http://localhost:8080");

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL);

    ChainBuilder register = exec(session -> {
        Map<String, Object> registerDetails = new HashMap<>();
        registerDetails.put("firstName", faker.name().firstName());
        registerDetails.put("lastName", faker.name().lastName());
        registerDetails.put("address", faker.address());
        return session.setAll(registerDetails);
    }).exec(
            http("Registration").post("/api/v1/auth/signup")
                    .body(ElFileBody("gold/registration.json")).asJson()
                    .check(status().is(200)));

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson().check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petListing = group("List pets and visits [My Pets]").on(exec(
            http("Get Pets by OwnerId [My Pets List]").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("Get visits [My Pets List]").get("/api/v1/visits")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")));

    ChainBuilder registerPet = group("Pet form").on(
            http("Get pet types [My Pets Form]").get("/api/v1/pets/types")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("Post a pet [My Pets Form]")
                    .post("/api/v1/pets")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")
                    .body(ElFileBody("newPets.json")).asJson()
                    .check(status().is(201)));

    ScenarioBuilder rampOwners = scenario("Gold Owners With a Ramp")
            .feed(csv("gold/owners-ramp.csv"))
            .exec(register, login, petListing, registerPet);

    {
        setUp(
                rampOwners.injectOpen(rampUsers(rampUsers).during(rampDuration)))
                .protocols(httpProtocol);
    }

}
