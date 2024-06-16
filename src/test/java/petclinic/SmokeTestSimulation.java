package petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class SmokeTestSimulation extends Simulation {

    String jsonLogin = "{" + "\"username\":\"owner1\"," + "\"password\":\"0wn3r\"" + "}";

    String newPet = "{" + "\"id\":null,"
            + "\"name\":\"Smoke\","
            + "\"birthDate\":\"2024-01-01\","
            + "\"type\":{\"id\":5,\"name\":\"bird\"},"
            + "\"owner\":{}}";

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080");

    ChainBuilder login = exec(
            http("Login").post("/api/v1/auth/signin").body(StringBody(jsonLogin)).asJson()
                    .check(jmesPath("token").saveAs("auth"),
                            jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder registerPet = exec(
            http("Save a pet")
                    .post("/api/v1/pets")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")
                    .body(StringBody(newPet)).asJson()
                    .check(status().is(201), jmesPath("id").saveAs("petId")));

    ChainBuilder delete = exec(http("Delete a pet")
            .delete("/api/v1/pets/#{petId}")
            .header("Authorization", "Bearer #{auth}")
            .header("Pricing-Token", "#{pricingToken}")
            .check(status().is(200),
                    jmesPath("message").is("Pet deleted!")));

    ScenarioBuilder owner = scenario("Smoke test").exec(login, registerPet, delete);

    {
        setUp(owner.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
