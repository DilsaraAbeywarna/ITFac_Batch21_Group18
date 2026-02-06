package starter.stepdefinitions.Api;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import utils.ResponseHolder;

public class UnauthorDelete {

    @When("Unauthenticated user sends DELETE request to {string}")
    public void unauthenticated_user_sends_delete_request_to(String endpoint) {
        Response response = SerenityRest.given()
                .baseUri("http://localhost:8080")
                .contentType("application/json")
                .log().all()
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
        
        ResponseHolder.setResponse(response);
    }
}
