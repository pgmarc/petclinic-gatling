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

public class VisitsConcurrentSimulation extends Simulation {

    Faker faker = new Faker(new Locale("es"), new Random(42));

    private final static String URL = System.getProperty("url", "http://localhost:8080");
    private final static int CONCURRENT_USERS = Integer.getInteger("users", 10);

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    Map<String, String> sentHeaders = Map.of(
            "Authorization", "Bearer #{auth}",
            "Pricing-Token", "#{pricingToken}");

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson().check(jmesPath("id").saveAs("userId"),
                    jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petListing = group("List my pets and their visits").on(exec(
            http("Get my pets").get("/api/v1/pets")
                    .queryParam("userId", "#{userId}")
                    .headers(sentHeaders)
                    .check(jmesPath("[0].id").saveAs("petId")),
            http("Get my pets visits").get("/api/v1/visits")
                    .headers(sentHeaders)),
            pause(Duration.ofMillis(300)));

    ChainBuilder visitForm = group("Visit form with loaded data").on(exec(
            http("Get details for my pet").get("/api/v1/pets/#{petId}")
                    .headers(sentHeaders)
                    .check(jsonPath("$").saveAs("pet")),
            http("Get vet information").get("/api/v1/vets")
                    .headers(sentHeaders)
                    .check(jmesPath("[0]").saveAs("vet"))),
            pause(Duration.ofMillis(300)));

    ChainBuilder registerVisit = exec(
            http("Book a visit for my pet").post("/api/v1/pets/#{petId}/visits")
                    .headers(sentHeaders)
                    .body(ElFileBody("visit.json")).asJson(),
            pause(Duration.ofMillis(300)));

    ScenarioBuilder concurrentOwners = scenario("Pet Owners creates visits for their pets")
            .feed(csv("common/visits-use-case.csv"))
            .exec(login, petListing, visitForm, registerVisit);

    {
        setUp(concurrentOwners.injectOpen(atOnceUsers(CONCURRENT_USERS)))
                .protocols(httpProtocol);
    }

}
