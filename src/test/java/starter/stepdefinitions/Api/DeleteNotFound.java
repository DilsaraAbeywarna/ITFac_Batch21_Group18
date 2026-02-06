package starter.stepdefinitions.Api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import utils.ResponseHolder;
import utils.TokenHolder;

public class DeleteNotFound {

    @Given("Admin is authenticated for retrieving sale")
    public void admin_is_authenticated_for_retrieving_sale() {
        String token = TokenHolder.getToken();
        Assertions.assertThat(token)
                .as("Admin authentication token should be available")
                .isNotNull()
                .isNotEmpty();
    }

    @When("Admin sends GET request to {string}")
    public void admin_sends_get_request_to(String endpoint) {
        String token = TokenHolder.getToken();
        
        Response response = SerenityRest.given()
                .baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .log().all()
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
        
        ResponseHolder.setResponse(response);
    }

    @And("Error message indicates sale not found")
    public void error_message_indicates_sale_not_found() {
        Response response = ResponseHolder.getResponse();
        Assertions.assertThat(response).as("Error response").isNotNull();
        
        // Check for error or message field
        String errorMessage = response.jsonPath().getString("message");
        if (errorMessage == null) {
            errorMessage = response.jsonPath().getString("error");
        }
        
        Assertions.assertThat(errorMessage)
                .as("Error message should be present")
                .isNotNull()
                .isNotEmpty();
        
        // Validate message indicates sale not found
        Assertions.assertThat(errorMessage.toLowerCase())
                .as("Error message should indicate sale not found")
                .containsAnyOf("not found", "does not exist", "not exist", "found", "invalid");
    }
}
