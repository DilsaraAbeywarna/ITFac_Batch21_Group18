package starter.stepdefinitions.Api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import utils.ResponseHolder;
import utils.TokenHolder;

public class CreateSale {

    @Given("Admin is authenticated for creating sales")
    public void admin_is_authenticated_for_creating_sales() {
        String token = TokenHolder.getToken();
        Assertions.assertThat(token)
                .as("Admin authentication token should be available")
                .isNotNull()
                .isNotEmpty();
    }

    @When("Admin sends POST request to {string} with quantity {int}")
    public void admin_sends_post_request_to_with_quantity(String endpoint, int quantity) {
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

    @And("Sale record is created in response")
    public void sale_record_is_created_in_response() {
        Response response = ResponseHolder.getResponse();
        Assertions.assertThat(response).as("Create sale API response").isNotNull();
        Assertions.assertThat(response.getBody().asString())
                .as("Response body should not be empty")
                .isNotEmpty();
    }

    @And("Response contains sale id")
    public void response_contains_sale_id() {
        Response response = ResponseHolder.getResponse();
        Object id = response.jsonPath().get("id");
        Assertions.assertThat(id)
                .as("Sale ID should be present in response")
                .isNotNull();
    }

    @And("Response contains correct plant details")
    public void response_contains_correct_plant_details() {
        Response response = ResponseHolder.getResponse();
        Object plant = response.jsonPath().get("plant");
        Assertions.assertThat(plant)
                .as("Plant details should be present in response")
                .isNotNull();
        
        // Validate plant has required fields
        Object plantId = response.jsonPath().get("plant.id");
        Assertions.assertThat(plantId)
                .as("Plant ID should be present")
                .isNotNull();
    }

    @And("Response contains sold quantity {int}")
    public void response_contains_sold_quantity(int expectedQuantity) {
        Response response = ResponseHolder.getResponse();
        Integer quantity = response.jsonPath().getInt("quantity");
        Assertions.assertThat(quantity)
                .as("Sold quantity in response")
                .isEqualTo(expectedQuantity);
    }

    @And("Response contains totalPrice")
    public void response_contains_total_price() {
        Response response = ResponseHolder.getResponse();
        Object totalPrice = response.jsonPath().get("totalPrice");
        Assertions.assertThat(totalPrice)
                .as("Total price should be present in response")
                .isNotNull();
        
        // Validate it's a positive number
        Double price = response.jsonPath().getDouble("totalPrice");
        Assertions.assertThat(price)
                .as("Total price should be positive")
                .isGreaterThan(0.0);
    }

    @And("Response contains soldAt timestamp")
    public void response_contains_sold_at_timestamp() {
        Response response = ResponseHolder.getResponse();
        Object soldAt = response.jsonPath().get("soldAt");
        Assertions.assertThat(soldAt)
                .as("SoldAt timestamp should be present in response")
                .isNotNull();
    }
}
