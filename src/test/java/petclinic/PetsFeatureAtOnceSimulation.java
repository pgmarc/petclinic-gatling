package petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class PetsFeatureAtOnceSimulation extends Simulation {

    private static final int PLATINUM_PLAN_MAX_PETS = 7;

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080");

    ChainBuilder register = feed(csv("platinum/owners.csv")).exec(
            http("Registration").post("/api/v1/auth/signup")
                    .body(ElFileBody("platinum/registration.json")).asJson()
                    .check(status().is(200)));

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson().check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder registerPet = feed(csv("platinum/pets.csv")).exec(
            http("Get Pets by OwnerId [My Pets List]").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("Get visits [My Pets List]").get("/api/v1/visits").header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            http("Get pet types [My Pets Form]").get("/api/v1/pets/types").header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("Post a pet [My Pets Form]")
                    .post("/api/v1/pets")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")
                    .body(ElFileBody("newPets.json")).asJson()
                    .check(status().is(201)));

    ScenarioBuilder platinumOwners = scenario("Planitum Owners register pets")
            .exec(register, login, repeat(PLATINUM_PLAN_MAX_PETS).on(registerPet));

    {
        setUp(platinumOwners.injectOpen(atOnceUsers(100))).protocols(httpProtocol);
    }

}
