package starter.stepdefinitions.Api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import utils.ResponseHolder;
import utils.TokenHolder;

public class UserViewSale {

    @Given("User is authenticated for viewing sales")
    public void user_is_authenticated_for_viewing_sales() {
        String token = TokenHolder.getToken();
        Assertions.assertThat(token)
                .as("User authentication token should be available")
                .isNotNull()
                .isNotEmpty();
    }

    @When("User sends GET request to sales API")
    public void user_sends_get_request_to_sales_api() {
        String token = TokenHolder.getToken();
        
        Response response = SerenityRest.given()
                .baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .log().all()
                .when()
                .get("/api/sales")
                .then()
                .log().all()
                .extract()
                .response();
        
        ResponseHolder.setResponse(response);
    }
}
