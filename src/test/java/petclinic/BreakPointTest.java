package petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class BreakPointTest extends Simulation {

    private final static String URL = System.getProperty("url", "http://localhost:8080");

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson()
            .check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ScenarioBuilder breakPointScenario = scenario("Scenario to check where system breaks")
            .feed(csv("pricing/random.csv").random())
            .exec(login);

    {
        setUp(breakPointScenario.injectOpen(constantUsersPerSec(0.5).during(120)))
                .protocols(httpProtocol);
    }

}
