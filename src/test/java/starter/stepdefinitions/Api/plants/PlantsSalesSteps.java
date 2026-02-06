package starter.stepdefinitions.Api.plants;

import java.util.Map;

import org.assertj.core.api.Assertions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import utils.ResponseHolder;
import utils.TokenHolder;

public class PlantsSalesSteps {

    @When("^Admin sends GET /api/plants/summary$")
    public void admin_sends_get_api_sales_request() {
        String token = TokenHolder.getToken();
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/plants/summary")
                .then()
                .extract()
                .response();
        ResponseHolder.setResponse("plants",response);        
    }

    @Then("Plant summary response status code is {int}")
    public void response_status_code_is(int statusCode) {
        Response response = ResponseHolder.getResponse("plants");
        System.out.println("Received status code: " + response.statusCode());
        Assertions.assertThat(response)
                .as("Plant summary API response")
                .isNotNull();
        Assertions.assertThat(response.statusCode())
                .as("HTTP status code")
                .isEqualTo(statusCode);
    }

    @Then("Plant summary Response body contains expected json format")
    public void response_body_contains_expected_json_format() {
        Response response = ResponseHolder.getResponse("plants");
        System.out.println("🔍 Validating response body format");

        // Verify the response structure using JsonPath directly
        response.then()
                .assertThat()
                .body("$", org.hamcrest.Matchers.hasKey("totalPlants"))
                .body("$", org.hamcrest.Matchers.hasKey("lowStockPlants"))
                .body("totalPlants", org.hamcrest.Matchers.instanceOf(Number.class))
                .body("lowStockPlants", org.hamcrest.Matchers.instanceOf(Number.class))
                .body("totalPlants", org.hamcrest.Matchers.greaterThanOrEqualTo(0))
                .body("lowStockPlants", org.hamcrest.Matchers.greaterThanOrEqualTo(0));

        // Verify exactly 2 keys
        Map<String, Object> summary = response.jsonPath().getMap("$");
        Assertions.assertThat(summary).hasSize(2);

        System.out.println("✓ Response format validated: " + summary);
    }

}
