package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class ComputerDatabaseSimulation extends Simulation {

    String jsonLogin = "{" + "\"username\":\"owner1\"," + "\"password\":\"0wn3r\"" + "}";

    String newPet = "{" + "\"id\":null,"
            + "\"name\":\"Smoke\","
            + "\"birthDate\":\"2024-01-01\","
            + "\"type\":{\"id\":5,\"name\":\"bird\"},"
            + "\"owner\":{}}";

    ChainBuilder singleRegistration = exec(
            http("Home").get("/"),
            pause(1),
            http("Login").get("/login"),
            http("Logged").post("/api/v1/auth/signin").body(StringBody(jsonLogin)).asJson()
                    .check(jmesPath("token").saveAs("auth"),
                            jmesPath("pricingToken").saveAs("pricingToken")),
            http("My Pets").get("/myPets"),
            pause(1),
            http("Add Pet").get("/myPets/new"),
            pause(1),
            http("Submit Pet")
                    .post("/api/v1/pets")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")
                    .body(StringBody(newPet)).asJson()
                    .check(status().is(201), jmesPath("id").saveAs("petId")),
            http("Delete pet")
                    .delete("/api/v1/pets/#{petId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")
                    .check(status().is(200),
                            jmesPath("message").is("Pet deleted!")));

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:3000")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0");

    ScenarioBuilder users = scenario("Smoke test").exec(singleRegistration);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
