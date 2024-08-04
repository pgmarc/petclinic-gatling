package petclinic.common;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class PetsRampSimulation extends Simulation {

    Faker faker = new Faker(new Locale("es"), new Random(42));

    private final static String URL = System.getProperty("url", "http://localhost:8080");
    private final static String TYPE = System.getProperty("type", "basic").toLowerCase();
    private final static int USERS = Integer.getInteger("users", 10);
    private final static int DURATION = Integer.getInteger("duration", 30);

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    ChainBuilder register = exec(session -> {
        return session.setAll(Map.of("firstName", faker.name().firstName(),
                "lastName", faker.name().lastName(),
                "address", faker.address().streetName()));
    }).exec(
            http("Registration").post("/api/v1/auth/signup")
                    .body(ElFileBody(String.format("%s/registration.json", TYPE))).asJson()
                    .check(status().is(200)));

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson().check(jmesPath("id").saveAs("userId"),
                    jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petListing = group("List my pets and their visits").on(exec(
            http("Get my pets").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("Get my pets visits").get("/api/v1/visits")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")));

    ChainBuilder formData = exec(
            http("Prefill data in the pet register form").get("/api/v1/pets/types")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(Duration.ofMillis(300)));

    ChainBuilder savePet = exec(http("Save my pet").post("/api/v1/pets")
            .header("Authorization", "Bearer #{auth}")
            .header("Pricing-Token", "#{pricingToken}")
            .body(ElFileBody("newPets.json")).asJson()
            .check(status().is(201)));

    ScenarioBuilder concurrentOwners = scenario(String.format("%s owners with ramp", TYPE.toUpperCase()))
            .feed(csv(String.format("%s/owners-ramp.csv", TYPE)))
            .exec(register, login, petListing, formData, savePet);

    {
        setUp(concurrentOwners.injectOpen(rampUsers(USERS).during(DURATION)))
                .protocols(httpProtocol);
    }

}
