package petclinic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.Random;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Test extends Simulation {

    Random random = new Random(42);

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080");

    ChainBuilder foo = exec(session -> session.set("id", random.nextInt(100)));

    ChainBuilder postPerson = exec(
            http("Random numbers").get("/number/#{id}"));

    ScenarioBuilder people = scenario("Random numbers tests").exec(foo, postPerson);

    {
        setUp(people.injectOpen(atOnceUsers(100))).protocols(httpProtocol);
    }
}
