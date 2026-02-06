package starter.stepdefinitions.Api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;
import utils.TokenHolder;

import java.util.List;
import java.util.Map;

public class SalesRetriveSteps {

    private Response response;

    @Given("Admin user is authenticated for Sales API")
    public void admin_user_is_authenticated_for_sales_api() {
        String token = TokenHolder.getToken();
        Assertions.assertThat(token)
                .as("Admin token")
                .isNotNull()
                .isNotBlank();
    }

    @When("^Admin sends GET /api/sales request$")
    public void admin_sends_get_api_sales_request() {
	String token = TokenHolder.getToken();
	response = SerenityRest.given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.header("Authorization", "Bearer " + token)
		.when()
		.get("/api/sales")
		.then()
		.extract()
		.response();
    }

    @Then("Response status code is {int}")
    public void response_status_code_is(int statusCode) {
	Assertions.assertThat(response)
		.as("Sales API response")
		.isNotNull();
	Assertions.assertThat(response.statusCode())
		.as("HTTP status code")
		.isEqualTo(statusCode);
    }

    @Then("Response body contains a list of sales")
    public void response_body_contains_a_list_of_sales() {
	List<Map<String, Object>> sales = response.jsonPath().getList("$");
	Assertions.assertThat(sales)
		.as("Sales list")
		.isNotNull()
		.isNotEmpty();
    }

    @Then("Each sale object includes required fields")
    public void each_sale_object_includes_required_fields() {
	List<Map<String, Object>> sales = response.jsonPath().getList("$");
	Assertions.assertThat(sales)
		.as("Sales list")
		.isNotNull()
		.isNotEmpty();

	for (Map<String, Object> sale : sales) {
	    Assertions.assertThat(sale)
		    .as("Sale object")
		    .containsKeys("id", "plant", "quantity", "totalPrice", "soldAt");

	    Object plant = sale.get("plant");
	    Assertions.assertThat(plant)
		    .as("Plant object")
		    .isNotNull();

	    if (plant instanceof Map) {
		Assertions.assertThat((Map<?, ?>) plant)
			.as("Plant details")
			.isNotEmpty();
	    } else {
		Assertions.fail("Plant details are not an object: " + plant);
	    }

	    Assertions.assertThat(sale.get("soldAt"))
		    .as("soldAt timestamp")
		    .isNotNull();
	}
    }
}
