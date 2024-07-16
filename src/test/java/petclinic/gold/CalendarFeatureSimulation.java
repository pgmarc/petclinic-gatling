package petclinic.gold;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class CalendarFeatureSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").disableCaching();

    ChainBuilder goldUserDetails = exec(session -> {
        return session.set("username", "owner4");
    });

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson()
            .check(jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder viewCalendar = group("[Gold Feature] View your pet visits on a calendar (haveCalendar)")
            .on(exec(http("GET Validate jwt expiration token").get("/api/v1/auth/validate?token=#{auth}"),
                    pause(1),
                    http("GET Get pet visits").get("/api/v1/visits")
                            .header("Authorization", "Bearer #{auth}")
                            .header("Pricing-Token", "#{pricingToken}")));

    ScenarioBuilder idleOwners = scenario("Gold user tries to view his calendar")
            .exec(goldUserDetails, login, viewCalendar);

    {
        setUp(idleOwners.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

}
