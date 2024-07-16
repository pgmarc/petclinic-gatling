package petclinic.basic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import petclinic.Recorder;

public class PetsFeature extends Simulation {

    private static final String URL = System.getProperty("url", "http://localhost:8080");

    private static final Integer simulationId = Integer.getInteger("id", 1);

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    Recorder recorder = new Recorder(URL + "/api/v1/metrics", this.getClass().getName(), simulationId, 100);

    Thread hilo = new Thread(recorder);

    ChainBuilder basicOwnerDetails = exec(session -> {
        return session.set("username", "owner9");
    });

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson().check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petListing = exec(
            http("Get Pets by OwnerId").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"));

    ScenarioBuilder basicOwnerChecksPets = scenario("Basic User consults pets").exec(basicOwnerDetails, login,
            petListing);

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
        setUp(basicOwnerChecksPets.injectOpen(atOnceUsers(1)).protocols(httpProtocol));
    }

}
