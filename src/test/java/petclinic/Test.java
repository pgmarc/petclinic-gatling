package petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test extends Simulation {

    String newPerson = "{" + "\"name\": \"John\"" + "}";

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080");

    ChainBuilder postPerson = exec(
            http("Post people").post("/people").body(StringBody(newPerson)).asJson());

    ScenarioBuilder people = scenario("Concurrency test").exec(postPerson);

    {
        setUp(people.injectOpen(atOnceUsers(100))).protocols(httpProtocol);
    }
}
