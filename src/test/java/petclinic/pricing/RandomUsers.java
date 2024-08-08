package petclinic.pricing;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;
import java.util.Map;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class RandomUsers extends Simulation {

    private final static String URL = System.getProperty("url", "http://localhost:8080");

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    Map<String, String> sentHeaders = Map.of(
            "Authorization", "Bearer #{auth}",
            "Pricing-Token", "#{pricingToken}");

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson()
            .check(jmesPath("id").saveAs("userId"),
                    jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")),
            pause(Duration.ofMillis(200)));

    ChainBuilder chooseEndpointAccordingToPlan = doSwitch("#{pricing}").on(
            onCase("basic").then(
                    repeat("#{times}").on(http("Get pets").get("/api/v1/pets?userId=#{userId}")
                            .headers(sentHeaders),
                            pause(Duration.ofMillis(200)))),
            onCase("gold").then(repeat("#{times}").on(http("Get vists").get("/api/v1/visits")
                    .headers(sentHeaders),
                    pause(Duration.ofMillis(200)))),
            onCase("platinum").then(
                    repeat("#{times}").on(http("Get consultations").get("/api/v1/consultations")
                            .headers(sentHeaders),
                            pause(Duration.ofMillis(200)))));

    ScenarioBuilder randomCall = scenario("Different type of owners call endpoints randomly")
            .feed(csv("pricing/random.csv").random())
            .exec(login, chooseEndpointAccordingToPlan);

    {
        setUp(randomCall.injectOpen(rampUsers(300).during(60))).protocols(httpProtocol);
    }

}
