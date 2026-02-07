package starter.stepdefinitions.Api;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import utils.ResponseHolder;

public class UnauthorNoSale {

    @When("^Unauthenticated user sends GET request to sales API$")
    public void unauthenticated_user_sends_get_request_to_sales_api() {
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/api/sales")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse(response);
    }

    @Then("Authentication error message is returned")
    public void authentication_error_message_is_returned() {
        Response response = ResponseHolder.getResponse();
        Assertions.assertThat(response)
                .as("Sales API response")
                .isNotNull();

        String responseBody = response.getBody().asString();
        
        Assertions.assertThat(responseBody)
                .as("Response body")
                .isNotNull()
                .isNotEmpty();

        // Check for common authentication error indicators
        boolean hasAuthError = responseBody.toLowerCase().contains("unauthorized") ||
                               responseBody.toLowerCase().contains("authentication") ||
                               responseBody.toLowerCase().contains("not authenticated") ||
                               responseBody.toLowerCase().contains("access denied") ||
                               response.jsonPath().get("error") != null ||
                               response.jsonPath().get("message") != null;

        Assertions.assertThat(hasAuthError)
                .as("Authentication error message present")
                .isTrue();
    }
}
