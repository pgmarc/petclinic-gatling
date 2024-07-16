package petclinic.platinum;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class ConsultationsFeatureSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").disableCaching();

    ChainBuilder platinumDetails = exec(session -> {
        return session.set("username", "owner1");
    });

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson()
            .check(jmesPath("id").saveAs("userId"),
                    jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petConsultations = exec(
            http("Get consultations").get("/api/v1/consultations")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"));

    ScenarioBuilder idleOwners = scenario("Platinum One User at once")
            .exec(platinumDetails, login, petConsultations);

    {
        setUp(idleOwners.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

}
