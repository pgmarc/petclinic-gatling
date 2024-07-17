package petclinic.platinum;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import petclinic.Recorder;

public class ConsultationsFeatureSimulation extends Simulation {

    private static final String URL = System.getProperty("url", "http://localhost:8080");

    private static final Integer simulationId = Integer.getInteger("id", 1);

    Recorder recorder = new Recorder(URL + "/api/v1/metrics", this.getClass().getName(), simulationId, 100);

    Thread hilo = new Thread(recorder);

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

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

    ScenarioBuilder consultationFeature = scenario("Platinum user gets consultations")
            .exec(platinumDetails, login, petConsultations);

    @Override
    public void before() {
        recorder.printHeader();
        hilo.start();
    }

    @Override
    public void after() {
        recorder.stopRecording();
        recorder.printFooter();
    }

    {
        setUp(consultationFeature.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

}
