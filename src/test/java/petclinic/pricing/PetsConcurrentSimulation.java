package petclinic.pricing;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class PetsConcurrentSimulation extends Simulation {

    Faker faker = new Faker(new Locale("es"), new Random(42));

    int basicUsers = Integer.getInteger("basic", 5);
    int goldUsers = Integer.getInteger("gold", 3);
    int platinumUsers = Integer.getInteger("platinum", 1);

    private final static String URL = System.getProperty("url", "http://localhost:8080");

    Map<String, String> sentHeaders = Map.of(
            "Authorization", "Bearer #{auth}",
            "Pricing-Token", "#{pricingToken}");

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    ChainBuilder genData = exec(session -> {
        Map<String, Object> registerDetails = new HashMap<>();
        registerDetails.put("firstName", faker.name().firstName());
        registerDetails.put("lastName", faker.name().lastName());
        registerDetails.put("address", faker.address());
        return session.setAll(registerDetails);
    });

    ChainBuilder registerBasic = genData.exec(
            http("Registration").post("/api/v1/auth/signup")
                    .body(ElFileBody("basic/registration.json")).asJson()
                    .check(status().is(200)));

    ChainBuilder registerGold = genData.exec(
            http("Registration").post("/api/v1/auth/signup")
                    .body(ElFileBody("gold/registration.json")).asJson()
                    .check(status().is(200)));

    ChainBuilder registerPlatinum = genData.exec(
            http("Registration").post("/api/v1/auth/signup")
                    .body(ElFileBody("platinum/registration.json")).asJson()
                    .check(status().is(200)));

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson()
            .check(jmesPath("id").saveAs("userId"),
                    jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petListing = group("My Pets list").on(exec(
            http("GET Pets by OwnerId").get("/api/v1/pets?userId=#{userId}")
                    .headers(sentHeaders),
            http("GET Visits").get("/api/v1/visits")
                    .headers(sentHeaders)),
            pause(Duration.ofMillis(300)));

    ChainBuilder registerPet = group("My Pets form").on(
            http("GET Pet Types").get("/api/v1/pets/types")
                    .headers(sentHeaders),
            pause(1),
            http("POST a pet")
                    .post("/api/v1/pets")
                    .headers(sentHeaders)
                    .body(ElFileBody("newPets.json")).asJson()
                    .check(status().is(201)));

    ScenarioBuilder basicOwners = scenario("Basic Owners concurrent")
            .feed(csv("pricing/basic-owners-con-final.csv"))
            .exec(registerBasic, login, petListing, registerPet);

    ScenarioBuilder goldOwners = scenario("Gold owners concurrent")
            .feed(csv("pricing/gold-owners-con-final.csv"))
            .exec(registerGold, login, petListing, registerPet);

    ScenarioBuilder platinumOwners = scenario("Platinum owners concurrent")
            .feed(csv("pricing/platinum-owners-con-final.csv"))
            .exec(registerPlatinum, login, petListing, registerPet);

    {
        setUp(
                basicOwners.injectOpen(atOnceUsers(basicUsers)),
                goldOwners.injectOpen(atOnceUsers(goldUsers)),
                platinumOwners.injectOpen(atOnceUsers(platinumUsers)))
                .protocols(httpProtocol);
    }

}
