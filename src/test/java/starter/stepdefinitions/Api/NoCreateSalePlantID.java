package starter.stepdefinitions.Api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.ResponseHolder;
import utils.TokenHolder;

public class NoCreateSalePlantID {

    @Given("Admin is authenticated for selling plants with invalid ID")
    public void admin_is_authenticated_for_selling_plants_with_invalid_id() {
        String token = TokenHolder.getToken();
        Assertions.assertThat(token)
                .as("Admin authentication token should be available")
                .isNotNull()
                .isNotEmpty();
    }

    @And("Error message indicates plant not found")
    public void error_message_indicates_plant_not_found() {
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
        
        // Validate message indicates plant not found
        Assertions.assertThat(errorMessage.toLowerCase())
                .as("Error message should indicate plant not found")
                .containsAnyOf("not found", "does not exist", "not exist", "plant", "invalid", "found");
    }
}
