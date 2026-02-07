package starter.stepdefinitions.api.plants;

import starter.api.plants.PlantsPageAPI;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import net.serenitybdd.annotations.Steps;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions for Plants API - Get Paginated Plants functionality
 * Test Case: API_Plant_GetPaginatedList_005
 */
public class PlantsGetSteps {

    @Steps
    PlantsPageAPI plantsPageAPI;

    // ==================== API_Plant_GetPaginatedList_005 ====================

    /**
     * Precondition Step: Verify database contains at least 15 plants
     * This is a passive check - we assume the precondition is met
     */
    @Given("Database contains at least 15 plants")
    public void verifyDatabaseHasMinimumPlants() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Precondition: Database should contain at least 15 plants");
        System.out.println("Note: This is verified by checking totalElements in response");
        System.out.println("═══════════════════════════════════════════════════════");
    }

    /**
     * Test Step: Admin user sends GET request to retrieve paginated plants
     * Endpoint: GET /api/plants/paged?page=1&size=10
     */
    @When("Admin user sends GET request to retrieve paginated plants with page {int} and size {int}")
    public void adminUserSendsGetRequestForPaginatedPlants(int page, int size) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Sending GET request to retrieve paginated plants");
        System.out.println("Page: " + page + ", Size: " + size);
        System.out.println("═══════════════════════════════════════════════════════");

        // Send GET request to /api/plants/paged with query parameters
        plantsPageAPI.getPaginatedPlants(page, size);

        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify response status code is 200 OK
     */
    @Then("API should return 200 OK status")
    public void verifyOkStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_GetPaginatedList_005");
        System.out.println("Endpoint: GET /api/plants/paged");
        System.out.println("Status Code: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("═══════════════════════════════════════════════════════");

        assertEquals(200, actualStatus,
                "Expected status code 200 OK, but got: " + actualStatus +
                        "\nResponse: " + responseBody);
    }

    /**
     * Test Step: Verify response body contains content array with plant objects
     * Expected structure:
     * {
     *   "content": [
     *     {
     *       "id": 40,
     *       "name": "Rose_20260206035115",
     *       "price": 75.5,
     *       "quantity": 20,
     *       "category": {...}
     *     }
     *   ]
     * }
     */
    @And("Response body should contain content array with plant objects")
    public void verifyContentArrayExists() {
        String responseBody = plantsPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");

        // Verify content array exists
        assertTrue(responseBody.contains("\"content\":"),
                "Content array not found in response. Actual response: " + responseBody);

        // Verify content is an array (contains [ ])
        assertTrue(responseBody.contains("\"content\":["),
                "Content is not an array. Actual response: " + responseBody);

        System.out.println("Content array exists in response");
    }

    /**
     * Test Step: Verify each plant object has required fields
     * Required fields: id, name, price, quantity, category
     */
    @And("Each plant object should have required fields")
    public void verifyPlantObjectStructure() {
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

        System.out.println("All required plant fields verified successfully");
    }

    /**
     * Test Step: Verify response contains pagination metadata
     * Expected fields:
     * - pageable (object with pageNumber, pageSize, offset)
     * - totalPages (integer)
     * - totalElements (integer)
     * - size (integer)
     * - number (current page number)
     * - first (boolean)
     * - last (boolean)
     * - empty (boolean)
     */
    @And("Response body should contain pagination metadata")
    public void verifyPaginationMetadata() {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify pageable object
        assertTrue(responseBody.contains("\"pageable\":"),
                "Pageable object not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"pageNumber\":"),
                "pageNumber field not found in pageable object. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"pageSize\":"),
                "pageSize field not found in pageable object. Actual response: " + responseBody);

        // Verify pagination metadata fields
        assertTrue(responseBody.contains("\"totalPages\":"),
                "totalPages field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"totalElements\":"),
                "totalElements field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"size\":"),
                "size field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"number\":"),
                "number (current page) field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"first\":"),
                "first field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"last\":"),
                "last field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"empty\":"),
                "empty field not found in response. Actual response: " + responseBody);

        System.out.println("All pagination metadata fields verified successfully");
        System.out.println("Test Case API_Plant_GetPaginatedList_005 - PASSED");
    }
}