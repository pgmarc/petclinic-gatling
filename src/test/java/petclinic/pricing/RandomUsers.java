package petclinic.pricing;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class RandomUsers extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").disableCaching();

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson()
            .check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder chooseEndpointAccordingToPlan = doSwitch("#{pricing}").on(
            onCase("basic").then(
                    http("Get pets").get("/api/v1/pets?userId=#{userId}")
                            .header("Authorization", "Bearer #{auth}")
                            .header("Pricing-Token", "#{pricingToken}")),
            onCase("gold").then(http("Get vists").get("/api/v1/visits")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")),
            onCase("platinum").then(
                    http("Get consultations").get("/api/v1/consultations")
                            .header("Authorization", "Bearer #{auth}")
                            .header("Pricing-Token", "#{pricingToken}")));

    ScenarioBuilder randomCall = scenario("Different type of owners call endpoints randomly")
            .feed(csv("pricing/random.csv").random())
            .exec(login, chooseEndpointAccordingToPlan);

    {
        setUp(randomCall.injectOpen(atOnceUsers(10))).protocols(httpProtocol);
    }
}
