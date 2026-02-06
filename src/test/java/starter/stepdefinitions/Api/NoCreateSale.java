package starter.stepdefinitions.Api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import utils.ResponseHolder;
import utils.TokenHolder;

public class NoCreateSale {

    @Given("Admin is authenticated for selling plants")
    public void admin_is_authenticated_for_selling_plants() {
        String token = TokenHolder.getToken();
        Assertions.assertThat(token)
                .as("Admin authentication token should be available")
                .isNotNull()
                .isNotEmpty();
    }

    @When("Admin sends POST request to {string} with invalid quantity {int}")
    public void admin_sends_post_request_to_with_invalid_quantity(String endpoint, int quantity) {
        String token = TokenHolder.getToken();
        
        Response response = SerenityRest.given()
                .baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + token)
                .queryParam("quantity", quantity)
                .contentType("application/json")
                .log().all()
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
        
        ResponseHolder.setResponse(response);
    }

    @And("Response contains error status")
    public void response_contains_error_status() {
        Response response = ResponseHolder.getResponse();
        Assertions.assertThat(response).as("Error response").isNotNull();
        
        // Check if status field exists (could be HTTP status or error status)
        Object status = response.jsonPath().get("status");
        Assertions.assertThat(status)
                .as("Error status should be present in response")
                .isNotNull();
    }

    @And("Response contains error message indicating invalid quantity")
    public void response_contains_error_message_indicating_invalid_quantity() {
        Response response = ResponseHolder.getResponse();
        
        // Check for error or message field
        String errorMessage = response.jsonPath().getString("message");
        if (errorMessage == null) {
            errorMessage = response.jsonPath().getString("error");
        }
        
        Assertions.assertThat(errorMessage)
                .as("Error message should be present")
                .isNotNull()
                .isNotEmpty();
        
        // Validate message indicates quantity/inventory issue
        Assertions.assertThat(errorMessage.toLowerCase())
                .as("Error message should indicate invalid quantity or insufficient stock")
                .containsAnyOf("quantity", "stock", "inventory", "insufficient", "invalid", "exceed");
    }

    @And("Response contains timestamp")
    public void response_contains_timestamp() {
        Response response = ResponseHolder.getResponse();
        Object timestamp = response.jsonPath().get("timestamp");
        Assertions.assertThat(timestamp)
                .as("Timestamp should be present in error response")
                .isNotNull();
    }
}
