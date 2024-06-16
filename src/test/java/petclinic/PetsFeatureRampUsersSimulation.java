package petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import net.datafaker.Faker;

public class PetsFeatureRampUsersSimulation extends Simulation {

    private static final int BASIC_PLAN_MAX_PETS = 2;

    Faker faker = new Faker(new Locale("es"), new Random(42));

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080");

    ChainBuilder register = exec(session -> {
        Map<String, Object> registerDetails = new HashMap<>();
        registerDetails.put("firstName", faker.name().firstName());
        registerDetails.put("lastName", faker.name().lastName());
        registerDetails.put("address", faker.address());
        return session.setAll(registerDetails);
    }).exec(
            http("Registration").post("/api/v1/auth/signup")
                    .body(ElFileBody("basic/registration.json")).asJson()
                    .check(status().is(200)));

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson().check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder registerPet = exec(
            http("Get Pets by OwnerId [My Pets List]").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("Get visits [My Pets List]").get("/api/v1/visits")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
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

    ScenarioBuilder basicOwners = scenario("Basic Owners register pets")
            .feed(csv("basic/owners.csv"))
            .feed(csv("basic/pets.csv"))
            .exec(register, login, registerPet);

    {
        setUp(
                basicOwners.injectOpen(rampUsers(500).during(30)))
                .protocols(httpProtocol);
    }

}
