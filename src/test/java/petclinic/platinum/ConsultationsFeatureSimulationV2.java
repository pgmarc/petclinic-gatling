package petclinic.platinum;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.Map;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class ConsultationsFeatureSimulationV2 extends Simulation {

    private static final String URL = System.getProperty("url", "http://localhost:8080");

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    ChainBuilder platinumDetails = exec(session -> {
        return session.setAll(Map.of("username", "owner1", "password", "0wn3r"));
    });

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("loginV1.json"))
            .asJson()
            .check(jmesPath("id").saveAs("userId"),
                    jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petConsultations = exec(
            http("Validate auth token").get("/api/v1/auth/validate?token=#{auth}"),
            http("Get consultations").get("/api/v1/consultations")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            http("Get plan").get("/api/v1/plan")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"));

    ChainBuilder createConsultation = exec(http("Validate auth token").get("/api/v1/auth/validate?token=#{auth}"),
            http("Ask vet something").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"));

    ScenarioBuilder consultationFeature = scenario("Platinum user gets consultations")
            .exec(platinumDetails, login, petConsultations);

    {
        setUp(consultationFeature.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

}
