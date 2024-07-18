package petclinic.pricing;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class PetsRampSimulation extends Simulation {

    Faker faker = new Faker(new Locale("es"), new Random(42));

    private final static String URL = System.getProperty("url", "http://localhost:8080");

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL);

    int basicUsers = Integer.getInteger("basic", 5);
    int goldUsers = Integer.getInteger("gold", 3);
    int platinumUsers = Integer.getInteger("platinum", 1);

    int basicUsersDuration = Integer.getInteger("basicDur", 60);
    int goldUsersDuration = Integer.getInteger("goldDur", 60);
    int platinumUsersDuration = Integer.getInteger("platinumDur", 60);

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
            .asJson().check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petListing = group("My Pets list").on(exec(
            http("GET Pets by OwnerId").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("GET Visits").get("/api/v1/visits")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")));

    ChainBuilder registerPet = group("My Pets form").on(
            http("GET Pet Types").get("/api/v1/pets/types")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("POST a pet")
                    .post("/api/v1/pets")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")
                    .body(ElFileBody("newPets.json")).asJson()
                    .check(status().is(201)));

    ScenarioBuilder basicOwners = scenario("Basic Owners ramp")
            .feed(csv("pricing/basic-owners-ramp-final.csv"))
            .exec(registerBasic, login, petListing, registerPet);

    ScenarioBuilder goldOwners = scenario("Gold owners ramp")
            .feed(csv("pricing/gold-owners-ramp-final.csv"))
            .exec(registerGold, login, petListing, registerPet);

    ScenarioBuilder platinumOwners = scenario("Platinum owners ramp")
            .feed(csv("pricing/platinum-owners-ramp-final.csv"))
            .exec(registerPlatinum, login, petListing, registerPet);

    {
        setUp(
                basicOwners.injectOpen(rampUsers(basicUsers).during(basicUsersDuration)),
                goldOwners.injectOpen(rampUsers(goldUsers).during(goldUsersDuration)),
                platinumOwners.injectOpen(rampUsers(platinumUsers).during(platinumUsers)))
                .protocols(httpProtocol);
    }

}
