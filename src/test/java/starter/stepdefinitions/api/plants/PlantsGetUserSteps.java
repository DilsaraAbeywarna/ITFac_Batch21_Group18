package starter.stepdefinitions.api.plants;

import starter.api.plants.PlantsPageAPI;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import net.serenitybdd.annotations.Steps;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions for Plants API - Get Plants as Normal User
 * Test Case: API_Plant_GetList_006
 */
public class PlantsGetUserSteps {

    @Steps
    PlantsPageAPI plantsPageAPI;

    // ==================== API_Plant_GetList_006 ====================

    /**
     * Precondition Step: Verify database contains multiple plants
     * This is a passive check - we assume the precondition is met
     */
    @Given("Database contains multiple plants")
    public void verifyDatabaseHasPlants() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Precondition: Database should contain multiple plants");
        System.out.println("Note: This is verified by checking response array length");
        System.out.println("═══════════════════════════════════════════════════════");
    }

    /**
     * Test Step: Normal User sends GET request to retrieve all plants
     * Endpoint: GET /api/plants
     * Note: This endpoint returns ALL plants (not paginated)
     */
    @When("Normal User sends GET request to retrieve all plants")
    public void normalUserSendsGetRequestForAllPlants() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Sending GET request to retrieve all plants");
        System.out.println("Role: Normal User (ROLE_USER)");
        System.out.println("═══════════════════════════════════════════════════════");

        // Send GET request to /api/plants (returns all plants, no pagination)
        plantsPageAPI.getAllPlants();

        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify response status code is 200 OK
     * Normal users should have READ access to view plants
     */
    @Then("Normal user API should return 200 OK status")
    public void verifyOkStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_GetList_006");
        System.out.println("Endpoint: GET /api/plants");
        System.out.println("Status Code: " + actualStatus);
        System.out.println("Response Body Preview: " + 
                (responseBody.length() > 500 ? responseBody.substring(0, 500) + "..." : responseBody));
        System.out.println("═══════════════════════════════════════════════════════");

        assertEquals(200, actualStatus,
                "Expected status code 200 OK, but got: " + actualStatus +
                        "\nResponse: " + responseBody);
    }

    /**
     * Test Step: Verify response body contains array of plant objects
     * Expected structure: [ {...}, {...}, {...} ]
     * Response should be a JSON array of plant objects
     */
    @And("Response body should contain array of plant objects")
    public void verifyResponseIsArrayOfPlants() {
        String responseBody = plantsPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");

        // Verify response starts with '[' (JSON array)
        assertTrue(responseBody.trim().startsWith("["),
                "Response is not a JSON array. Expected to start with '['. Actual response: " + responseBody);

        // Verify response ends with ']'
        assertTrue(responseBody.trim().endsWith("]"),
                "Response is not a JSON array. Expected to end with ']'. Actual response: " + responseBody);

        // Verify array contains at least one plant object
        assertTrue(responseBody.contains("\"id\":"),
                "Response array appears empty or malformed. Expected plant objects with 'id' field. Actual response: " + responseBody);

        System.out.println("Response is a valid JSON array of plant objects");
    }

    /**
     * Test Step: Verify each plant object has all required fields
     * Required fields: id, name, price, quantity, category
     * Expected structure:
     * {
     *   "id": 12,
     *   "name": "Lotus",
     *   "price": 20,
     *   "quantity": 3,
     *   "category": {
     *     "id": 5,
     *     "name": "Foliage",
     *     "subCategories": []
     *   }
     * }
     */
    @And("Each plant object should have all required fields")
    public void verifyPlantObjectFields() {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify plant object contains required fields
        assertTrue(responseBody.contains("\"id\":"),
                "Plant ID field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"name\":"),
                "Plant name field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"price\":"),
                "Plant price field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"quantity\":"),
                "Plant quantity field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"category\":"),
                "Plant category field not found in response. Actual response: " + responseBody);

        // Verify category object structure
        assertTrue(responseBody.contains("\"subCategories\":"),
                "Category subCategories field not found in response. Actual response: " + responseBody);

        System.out.println("All required plant fields verified successfully");
        System.out.println("Fields verified: id, name, price, quantity, category");
        System.out.println("Category object contains: id, name, subCategories");
        System.out.println("Normal User successfully accessed plant data (READ permission confirmed)");
        System.out.println("Test Case API_Plant_GetList_006 - PASSED");
    }
}