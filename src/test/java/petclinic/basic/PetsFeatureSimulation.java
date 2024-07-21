package petclinic.basic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import petclinic.Recorder;

public class PetsFeatureSimulation extends Simulation {

    private static final String URL = System.getProperty("url", "http://localhost:8080");

    private static final Integer simulationId = Integer.getInteger("id", 1);

    private static final String ownerType = System.getProperty("type", "basic").toLowerCase();

    HttpProtocolBuilder httpProtocol = http.baseUrl(URL).disableCaching();

    Recorder recorder = new Recorder(URL + "/api/v1/metrics", this.getClass().getName() + "-" + ownerType.toLowerCase(),
            simulationId, 100);

    Thread hilo = new Thread(recorder);

    ChainBuilder ownerDetails = doSwitch(ownerType).on(
            onCase("basic").then(exec(session -> session.set("username", "owner9"))),
            onCase("gold").then(exec(session -> session.set("username", "owner4"))),
            onCase("platinum").then(exec(session -> session.set("username", "owner1")))

    );

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson().check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petListing = exec(
            http("Get Pets by OwnerId").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"));

    ScenarioBuilder basicOwnerChecksPets = scenario(ownerType.toUpperCase() + " user consults pets").exec(ownerDetails,
            login,
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
