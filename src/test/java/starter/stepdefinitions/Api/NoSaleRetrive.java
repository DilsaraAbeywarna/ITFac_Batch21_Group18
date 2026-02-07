package starter.stepdefinitions.Api;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.assertj.core.api.Assertions;

import java.util.List;

public class NoSaleRetrive {

    @Then("Response body is an empty array")
    public void response_body_is_an_empty_array() {
        Response response = SerenityRest.lastResponse();
        Assertions.assertThat(response)
                .as("Sales API response")
                .isNotNull();
        
        List<Object> sales = response.jsonPath().getList("$");
        Assertions.assertThat(sales)
                .as("Sales list")
                .isNotNull()
                .isEmpty();
    }
}
