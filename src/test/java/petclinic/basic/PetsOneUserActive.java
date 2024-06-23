package petclinic.basic;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import net.datafaker.Faker;

public class PetsOneUserActive extends Simulation {

    Faker faker = new Faker(new Locale("es"), new Random(42));

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080");

    ChainBuilder register = exec(session -> {
        Map<String, Object> registerDetails = new HashMap<>();
        registerDetails.put("firstName", faker.name().firstName());
        registerDetails.put("lastName", faker.name().lastName());
        registerDetails.put("address", faker.address());
        registerDetails.put("petName", "BasicIdlePet");
        registerDetails.put("username", "BasicIdle");
        return session.setAll(registerDetails);
    }).exec(
            http("Registration").post("/api/v1/auth/signup")
                    .body(ElFileBody("basic/registration.json")).asJson()
                    .check(status().is(200)));

    ChainBuilder login = exec(http("Login")
            .post("/api/v1/auth/signin").body(ElFileBody("login.json"))
            .asJson().check(jmesPath("id").saveAs("userId"), jmesPath("token").saveAs("auth"),
                    jmesPath("pricingToken").saveAs("pricingToken")));

    ChainBuilder petListing = group("List pets and visits [My Pets]").on(exec(
            http("Get Pets by OwnerId [My Pets List]").get("/api/v1/pets?userId=#{userId}")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("Get visits [My Pets List]").get("/api/v1/visits")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")));

    ChainBuilder registerPet = group("Pet form").on(
            http("Get pet types [My Pets Form]").get("/api/v1/pets/types")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}"),
            pause(1),
            http("Post a pet [My Pets Form]")
                    .post("/api/v1/pets")
                    .header("Authorization", "Bearer #{auth}")
                    .header("Pricing-Token", "#{pricingToken}")
                    .body(ElFileBody("newPets.json")).asJson()
                    .check(status().is(201), jmesPath("id").saveAs("petId")));

    ChainBuilder deletePet = exec(http("Delete a pet")
            .delete("/api/v1/pets/#{petId}")
            .header("Authorization", "Bearer #{auth}")
            .header("Pricing-Token", "#{pricingToken}")
            .check(status().is(200),
                    jmesPath("message").is("Pet deleted!")));

    ChainBuilder loginAdmin = exec(http("Login Admin")
            .post("/api/v1/auth/signin").body(ElFileBody("adminLogin.json"))
            .asJson().check(jmesPath("token").saveAs("auth")));

    ChainBuilder deleteOwner = exec(http("Delete an owner")
            .delete("/api/v1/users/#{userId}")
            .header("Authorization", "Bearer #{auth}")
            .check(status().is(200)));

    {
        /*
         * PopulationBuilder[] users = IntStream.range(0, 100)
         * .mapToObj(i -> scenario("Basic One User " + i)
         * .exec(register, login, petListing, registerPet, deletePet, deleteOwner)
         * .injectOpen(atOnceUsers(1), nothingFor(1)).protocols(httpProtocol))
         * .toArray(PopulationBuilder[]::new);
         */
        setUp(scenario("Basic User 1").exec(register, login, petListing, registerPet, deletePet, loginAdmin,
                deleteOwner).injectOpen(atOnceUsers(1), nothingFor(2)).protocols(httpProtocol)
                .andThen(scenario("Basic User 2").exec(register, login, petListing, registerPet, deletePet, loginAdmin,
                        deleteOwner).injectOpen(atOnceUsers(1)).protocols(httpProtocol)));
    }

}
