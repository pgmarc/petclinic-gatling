package petclinic.platinum;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.Map;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class ConsultationsConcurrentSimulation extends Simulation {

    private static final String URL = System.getProperty("url", "http://localhost:8080");

    private static final int CONCURRENT_USERS = Integer.getInteger("users", 10);

    Map<String, String> sentHeaders = Map.of(
            "Authorization", "Bearer #{auth}",
            "Pricing-Token", "#{pricingToken}");

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson()
            .check(jmesPath("id").saveAs("userId"),
                    jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petConsultations = exec(
            http("Get consultations").get("/api/v1/consultations")
                    .headers(sentHeaders));

    ChainBuilder consultationForm = exec(http("Get my pets").get("/api/v1/pets")
            .asJson()
            .queryParam("userId", "#{userId}")
            .headers(sentHeaders)
            .check(jmesPath("[0]").saveAs("pet"),
                    jmesPath("[0].owner").saveAs("owner")));

    ChainBuilder registerConsultation = exec(http("Post consultation to the vet").post("/api/v1/consultations")
            .asJson()
            .headers(sentHeaders)
            .body(ElFileBody("consultation.json"))
            .check(jsonPath("$.id").saveAs("consultationId")));

    ChainBuilder enterConsultationChat = exec(
            http("Get in the consultation chat")
                    .get("/api/v1/consultations/#{consultationId}/tickets")
                    .headers(sentHeaders));

    ChainBuilder sendConsultation = exec(
            http("Send a message in the chat to the vet")
                    .post("/api/v1/consultations/#{consultationId}/tickets")
                    .asJson()
                    .headers(sentHeaders)
                    .body(ElFileBody("ticket.json")));

    ScenarioBuilder consultationFeature = scenario("Platinum owners make consultations to vets [CONCURRENT]")
            .feed(csv("platinum/consultation-use-case.csv"))
            .exec(login, petConsultations, consultationForm, registerConsultation,
                    enterConsultationChat, sendConsultation);

    {
        setUp(consultationFeature.injectOpen(atOnceUsers(CONCURRENT_USERS))).protocols(httpProtocol);
    }

}
