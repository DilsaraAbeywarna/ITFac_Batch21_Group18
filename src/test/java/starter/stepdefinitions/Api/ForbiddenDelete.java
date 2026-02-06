package starter.stepdefinitions.Api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import utils.ResponseHolder;
import utils.TokenHolder;

public class ForbiddenDelete {

    @Given("Non-Admin user is authenticated for deleting sale")
    public void non_admin_user_is_authenticated_for_deleting_sale() {
        String token = TokenHolder.getToken();
        Assertions.assertThat(token)
                .as("Non-Admin user authentication token should be available")
                .isNotNull()
                .isNotEmpty();
    }

    @When("Non-Admin user sends DELETE request to {string}")
    public void non_admin_user_sends_delete_request_to(String endpoint) {
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

    @And("Error message indicates forbidden access")
    public void error_message_indicates_forbidden_access() {
        Response response = ResponseHolder.getResponse();
        Assertions.assertThat(response).as("Forbidden access response").isNotNull();
        
        // Check for error or message field
        String errorMessage = response.jsonPath().getString("message");
        if (errorMessage == null) {
            errorMessage = response.jsonPath().getString("error");
        }
        
        Assertions.assertThat(errorMessage)
                .as("Error message should be present")
                .isNotNull()
                .isNotEmpty();
        
        // Validate message indicates forbidden/access denied
        Assertions.assertThat(errorMessage.toLowerCase())
                .as("Error message should indicate forbidden access or insufficient permissions")
                .containsAnyOf("forbidden", "access denied", "permission", "not authorized", "insufficient");
    }
}
