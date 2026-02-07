package starter.stepdefinitions.api.plants;

import starter.api.plants.PlantsPageAPI;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import net.serenitybdd.annotations.Steps;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions for Plants API - Create Plant Forbidden
 * Test Case: API_Plant_CreateForbidden_009
 */
public class PlantsCreateForbiddenSteps {

    @Steps
    PlantsPageAPI plantsPageAPI;

    private int categoryId;
    private String plantName;
    private Map<String, Object> plantData;

    // ==================== API_Plant_CreateForbidden_009 ====================

    /**
     * Precondition Step: Verify user is NOT an Admin
     * This is a passive check - we assume the user has NORMAL_USER role
     */
    @Given("User is NOT an Admin")
    public void verifyUserIsNotAdmin() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Precondition: User is NOT an Admin");
        System.out.println("Role: Normal User (ROLE_USER)");
        System.out.println("Note: JWT token with ROLE_USER is already set via @nonadminapi hook");
        System.out.println("═══════════════════════════════════════════════════════");
    }

    /**
     * Test Step: Normal User sends POST request to create a plant
     * Endpoint: POST /api/plants/category/{categoryId}
     * Request Body: {"name": "Daisy", "price": 40, "quantity": 10}
     */
    @When("Normal User sends POST request to create plant {string} under category {int} with price {int} and quantity {int}")
    public void normalUserSendsPostRequestToCreatePlant(String plantName, int categoryId, int price, int quantity) {
        this.categoryId = categoryId;
        this.plantName = plantName;
        
        // Build request body
        this.plantData = new HashMap<>();
        this.plantData.put("name", plantName);
        this.plantData.put("price", price);
        this.plantData.put("quantity", quantity);

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Sending POST request to create plant");
        System.out.println("Endpoint: POST /api/plants/category/" + categoryId);
        System.out.println("Role: Normal User (ROLE_USER)");
        System.out.println("Request Body: " + this.plantData);
        System.out.println("Expected: 403 Forbidden (User lacks Admin permissions)");
        System.out.println("═══════════════════════════════════════════════════════");

        // Send POST request to create plant
        plantsPageAPI.createPlantUnderCategory(categoryId, this.plantData);

        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify response status code is 403 Forbidden
     * Normal users should NOT have CREATE access (Admin only)
     */
    @Then("Create API should return 403 Forbidden status")
    public void verifyCreateForbiddenStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_CreateForbidden_009");
        System.out.println("Endpoint: POST /api/plants/category/" + categoryId);
        System.out.println("Status Code: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("═══════════════════════════════════════════════════════");

        assertEquals(403, actualStatus,
                "Expected status code 403 Forbidden, but got: " + actualStatus +
                        "\nResponse: " + responseBody +
                        "\nReason: Normal User should NOT have permission to create plants");
    }

    /**
     * Test Step: Verify response contains authorization error
     * Expected: "Forbidden" error indicating insufficient permissions
     */
    @And("Response should contain authorization error")
    public void verifyAuthorizationError() {
        String responseBody = plantsPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");

        // Verify error field contains "Forbidden"
        assertTrue(responseBody.contains("\"error\":\"Forbidden\"") || 
                   responseBody.contains("\"error\": \"Forbidden\""),
                "Error field 'Forbidden' not found in response. Actual response: " + responseBody);

        System.out.println("Response contains authorization error: Forbidden");
    }

    /**
     * Test Step: Verify error response is properly formatted
     * Expected structure:
     * {
     *   "timestamp": "2026-02-06T12:06:21.989+00:00",
     *   "status": 403,
     *   "error": "Forbidden",
     *   "path": "/api/plants/category/4"
     * }
     */
    @And("Error response should be properly formatted")
    public void verifyErrorResponseFormat() {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify all required error fields
        assertTrue(responseBody.contains("\"timestamp\":"),
                "timestamp field not found in error response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"status\":403") || responseBody.contains("\"status\": 403"),
                "status field with value 403 not found in error response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"error\":\"Forbidden\"") || 
                   responseBody.contains("\"error\": \"Forbidden\""),
                "error field 'Forbidden' not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"path\":") && responseBody.contains("/api/plants/category/"),
                "path field not found in error response. Actual response: " + responseBody);

        System.out.println("Error response is properly formatted with all required fields");
    }

    /**
     * Test Step: Verify plant was NOT created in the database
     * Since we got 403 Forbidden, the plant should NOT exist in the system
     * This can be verified by checking the response - no plant ID is returned
     */
    @And("Plant {string} should NOT be created in the database")
    public void verifyPlantNotCreated(String expectedPlantName) {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify response does NOT contain plant data (id, name, etc.)
        assertFalse(responseBody.contains("\"id\":") && responseBody.contains("\"name\":\"" + expectedPlantName + "\""),
                "Plant appears to have been created despite 403 Forbidden. Response: " + responseBody);

        // Verify response is an error response (contains "error" field)
        assertTrue(responseBody.contains("\"error\":"),
                "Response does not appear to be an error response. Response: " + responseBody);

        System.out.println("Plant '" + expectedPlantName + "' was NOT created (as expected)");
        System.out.println("Role-based access control is working correctly");
        System.out.println("Only Admins can create plants (Normal Users receive 403 Forbidden)");
        System.out.println("Test Case API_Plant_CreateForbidden_009 - PASSED");
    }
}
