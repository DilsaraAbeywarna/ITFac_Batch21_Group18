package starter.stepdefinitions.Api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import utils.ResponseHolder;
import utils.TokenHolder;

public class DeleteSucces {

    @Given("Admin is authenticated for deleting sale")
    public void admin_is_authenticated_for_deleting_sale() {
        String token = TokenHolder.getToken();
        Assertions.assertThat(token)
                .as("Admin authentication token should be available")
                .isNotNull()
                .isNotEmpty();
    }

    @When("Admin sends DELETE request to {string}")
    public void admin_sends_delete_request_to(String endpoint) {
        String token = TokenHolder.getToken();
        
        Response response = SerenityRest.given()
                .baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + token)
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
