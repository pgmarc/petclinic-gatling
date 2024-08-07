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

public class PetsConcurrentSimulation extends Simulation {

    Faker faker = new Faker(new Locale("es"), new Random(42));

    private final static String URL = System.getProperty("url", "http://localhost:8080");
    private final static String TYPE = System.getProperty("type", "basic").toLowerCase();
    private final static int CONCURRENT_USERS = Integer.getInteger("users", 10);

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    Map<String, String> sentHeaders = Map.of(
            "Authorization", "Bearer #{auth}",
            "Pricing-Token", "#{pricingToken}");

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
            http("Get my pets").get("/api/v1/pets")
                    .queryParam("userId", "#{userId}")
                    .headers(sentHeaders),
            http("Get my pets visits").get("/api/v1/visits")
                    .headers(sentHeaders)),
            pause(Duration.ofMillis(300)));

    ChainBuilder formData = exec(
            http("Prefill data in the pet register form").get("/api/v1/pets/types")
                    .headers(sentHeaders)
                    .check(jsonPath("$").ofList().saveAs("type")),
            pause(Duration.ofMillis(300)));

    ChainBuilder savePet = exec(http("Save my pet").post("/api/v1/pets")
            .headers(sentHeaders)
            .body(ElFileBody("newPetsV2.json")).asJson()
            .check(status().is(201), jmesPath("id").saveAs("petId")),
            pause(Duration.ofMillis(300)));

    ChainBuilder deletePet = exec(http("Delete recently added pet").delete("/api/v1/pets/#{petId}")
            .headers(sentHeaders));

    ScenarioBuilder concurrentOwners = scenario(String.format("%s owners concurrent", TYPE.toUpperCase()))
            .feed(csv(String.format("%s/owners-con.csv", TYPE)))
            .exec(register, login, petListing, formData, savePet, petListing, deletePet, petListing);

    {
        setUp(concurrentOwners.injectOpen(atOnceUsers(CONCURRENT_USERS)))
                .protocols(httpProtocol);
    }

}
