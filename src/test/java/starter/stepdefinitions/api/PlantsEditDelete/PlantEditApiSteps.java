package starter.stepdefinitions.api.PlantsEditDelete;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.api.PlantsEditDeleteApi;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PlantEditApiSteps {

    private PlantsEditDeleteApi plantApiClient = new PlantsEditDeleteApi();
    private Response response;
    private String bearerToken;

    @Given("the Plant API base URL is {string}")
    public void thePlantApiBaseUrlIs(String baseUrl) {
        plantApiClient.setBaseUrl(baseUrl);
    }

    @Given("Admin is authenticated with valid Bearer Token")
    public void adminIsAuthenticatedWithValidBearerToken() {
        // Login to get actual bearer token
        Response loginResponse = plantApiClient.login("admin", "admin123");
        
        if (loginResponse.getStatusCode() == 200) {
            // Extract token from response
            this.bearerToken = loginResponse.jsonPath().getString("token");
            if (this.bearerToken == null || this.bearerToken.isEmpty()) {
                // Try alternative token field names
                this.bearerToken = loginResponse.jsonPath().getString("accessToken");
            }
            if (this.bearerToken == null || this.bearerToken.isEmpty()) {
                this.bearerToken = loginResponse.jsonPath().getString("data.token");
            }
        } else {
            this.bearerToken = "";
        }
        
        plantApiClient.setBearerToken(bearerToken);
    }

    @Given("Test User is authenticated with valid Bearer Token")
    public void testUserIsAuthenticatedWithValidBearerToken() {
        // Login as testuser to get bearer token
        Response loginResponse = plantApiClient.login("testuser", "test123");
        
        if (loginResponse.getStatusCode() == 200) {
            // Extract token from response
            this.bearerToken = loginResponse.jsonPath().getString("token");
            if (this.bearerToken == null || this.bearerToken.isEmpty()) {
                // Try alternative token field names
                this.bearerToken = loginResponse.jsonPath().getString("accessToken");
            }
            if (this.bearerToken == null || this.bearerToken.isEmpty()) {
                this.bearerToken = loginResponse.jsonPath().getString("data.token");
            }
        } else {
            this.bearerToken = "";
        }
        
        plantApiClient.setBearerToken(bearerToken);
    }

    @Given("a plant with id {int} exists in the system")
    public void aPlantWithIdExistsInTheSystem(Integer plantId) {
        // Verify the specific plant exists
        Response checkResponse = plantApiClient.getPlant(plantId);
        
        if (checkResponse.getStatusCode() == 404) {
            throw new AssertionError("Plant with ID " + plantId + " not found in the system");
        }
    }

    @When("Admin sends a PUT request to {string} with request body:")
    public void adminSendsAPutRequestToWithRequestBody(String endpoint, String requestBody) {
        response = plantApiClient.updatePlant(endpoint, requestBody);
    }

    @When("Test User sends a PUT request to {string} with request body:")
    public void testUserSendsAPutRequestToWithRequestBody(String endpoint, String requestBody) {
        response = plantApiClient.updatePlant(endpoint, requestBody);
    }

    @When("Admin sends a DELETE request to {string}")
    public void adminSendsADeleteRequestTo(String endpoint) {
        response = plantApiClient.deletePlant(endpoint);
    }

    @When("Test User sends a DELETE request to {string}")
    public void testUserSendsADeleteRequestTo(String endpoint) {
        response = plantApiClient.deletePlant(endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertThat(response.getStatusCode())
                .as("Expected HTTP status code " + expectedStatusCode)
                .isEqualTo(expectedStatusCode);
    }

    @And("the response body should contain:")
    public void theResponseBodyShouldContain(Map<String, String> expectedFields) {
        for (Map.Entry<String, String> entry : expectedFields.entrySet()) {
            String field = entry.getKey();
            String expectedValue = entry.getValue();
            
            // Handle numeric fields
            if (field.equals("price")) {
                Double actualPrice = response.jsonPath().getDouble(field);
                Double expectedPrice = Double.parseDouble(expectedValue);
                assertThat(actualPrice)
                        .as("Field: " + field)
                        .isEqualTo(expectedPrice);
            } else if (field.equals("quantity")) {
                Integer actualQuantity = response.jsonPath().getInt(field);
                Integer expectedQuantity = Integer.parseInt(expectedValue);
                assertThat(actualQuantity)
                        .as("Field: " + field)
                        .isEqualTo(expectedQuantity);
            } else {
                String actualValue = response.jsonPath().getString(field);
                assertThat(actualValue)
                        .as("Field: " + field)
                        .isEqualTo(expectedValue);
            }
        }
    }
}
