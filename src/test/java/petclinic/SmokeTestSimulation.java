package petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.Map;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class SmokeTestSimulation extends Simulation {

    private final static String URL = System.getProperty("url", "http://localhost:8080");

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    Map<String, String> sentHeaders = Map.of(
            "Authorization", "Bearer #{auth}",
            "Pricing-Token", "#{pricingToken}");

    ChainBuilder userDetails = exec(session -> session.setAll(
            Map.of("username", "owner1",
                    "petName", "Smoky")));

    ChainBuilder login = exec(
            http("Get clinics").get("/api/v1/clinics").asJson(),
            http("Login").post("/api/v1/auth/signin").body(ElFileBody("login.json"))
                    .asJson()
                    .check(jmesPath("token").saveAs("auth"),
                            jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder deletePet = exec(http("Delete a pet").delete("/api/v1/pets/#{petId}")
            .headers(sentHeaders).asJson()
            .check(status().is(200),
                    jmesPath("message").is("Pet deleted!")));

    ChainBuilder registerPet = repeat(7, "petCounter")
            .on(exec(session -> session.set("petName", "Smoke" + session.get("petCounter"))).exec(
                    http("Save a pet")
                            .post("/api/v1/pets")
                            .headers(sentHeaders)
                            .body(ElFileBody("newPets.json")).asJson()
                            .check(status().is(201), jmesPath("id").saveAs("petId"))),
                    deletePet);

    ScenarioBuilder owner = scenario("Smoke test to check if everything is fine")
            .exec(userDetails, login, registerPet);

    {
        setUp(owner.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
