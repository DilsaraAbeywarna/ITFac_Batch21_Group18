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
 * Step Definitions for Plants API - Unauthorized Access
 * Test Case: API_Plant_UnauthorizedAccess_010
 */
public class PlantsUnauthorizedAccessSteps {

    @Steps
    PlantsPageAPI plantsPageAPI;

    private int categoryId;
    private String plantName;
    private Map<String, Object> plantData;

    // ==================== API_Plant_UnauthorizedAccess_010 ====================

    /**
     * Precondition Step: Verify no authentication token is provided
     * This is a passive check - we will explicitly NOT set any token
     */
    @Given("No authentication token is provided")
    public void verifyNoAuthenticationToken() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Precondition: No authentication token is provided");
        System.out.println("Authorization Header: NOT INCLUDED");
        System.out.println("Note: Request will be sent WITHOUT any authentication");
        System.out.println("═══════════════════════════════════════════════════════");
    }

    /**
     * Test Step: Unauthenticated user sends POST request to create a plant
     * Endpoint: POST /api/plants/category/{categoryId}
     * Request Body: {"name": "Orchid", "price": 90, "quantity": 5}
     * IMPORTANT: No Authorization header is included
     */
    @When("Unauthenticated user sends POST request to create plant {string} under category {int} with price {int} and quantity {int}")
    public void unauthenticatedUserSendsPostRequestToCreatePlant(String plantName, int categoryId, int price, int quantity) {
        this.categoryId = categoryId;
        this.plantName = plantName;
        
        // Build request body
        this.plantData = new HashMap<>();
        this.plantData.put("name", plantName);
        this.plantData.put("price", price);
        this.plantData.put("quantity", quantity);

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Sending POST request WITHOUT authentication");
        System.out.println("Endpoint: POST /api/plants/category/" + categoryId);
        System.out.println("Authorization: NONE (No token provided)");
        System.out.println("Request Body: " + this.plantData);
        System.out.println("Expected: 401 Unauthorized (Authentication required)");
        System.out.println("═══════════════════════════════════════════════════════");

        // Send POST request WITHOUT authentication token
        plantsPageAPI.createPlantWithoutAuth(categoryId, this.plantData);

        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify response status code is 401 Unauthorized
     * Unauthenticated users should receive 401 error
     */
    @Then("Create API should return 401 Unauthorized status")
    public void verifyCreateUnauthorizedStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_UnauthorizedAccess_010");
        System.out.println("Endpoint: POST /api/plants/category/" + categoryId);
        System.out.println("Status Code: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("═══════════════════════════════════════════════════════");

        assertEquals(401, actualStatus,
                "Expected status code 401 Unauthorized, but got: " + actualStatus +
                        "\nResponse: " + responseBody +
                        "\nReason: Request without authentication should return 401");
    }

    /**
     * Test Step: Verify response contains authentication error
     * Expected: "UNAUTHORIZED" error indicating missing authentication
     */
    @And("Response should contain authentication error")
    public void verifyAuthenticationError() {
        String responseBody = plantsPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");

        // Verify error field contains "UNAUTHORIZED"
        assertTrue(responseBody.contains("\"error\":\"UNAUTHORIZED\"") || 
                   responseBody.contains("\"error\": \"UNAUTHORIZED\""),
                "Error field 'UNAUTHORIZED' not found in response. Actual response: " + responseBody);

        System.out.println("Response contains authentication error: UNAUTHORIZED");
    }

    /**
     * Test Step: Verify error message indicates authentication is required
     * Expected message: "Unauthorized - Use Basic Auth or JWT"
     */
    @And("Error message should indicate authentication is required")
    public void verifyAuthenticationRequiredMessage() {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify message field exists and contains authentication-related text
        assertTrue(responseBody.contains("\"message\":"),
                "message field not found in error response. Actual response: " + responseBody);

        // Verify message mentions authentication (flexible check for different possible messages)
        assertTrue(responseBody.toLowerCase().contains("unauthorized") || 
                   responseBody.toLowerCase().contains("auth") ||
                   responseBody.toLowerCase().contains("jwt"),
                "Error message does not indicate authentication requirement. Actual response: " + responseBody);

        System.out.println("Error message indicates authentication is required");
    }

    /**
     * Test Step: Verify error response is properly formatted
     * Expected structure:
     * {
     *   "status": 401,
     *   "error": "UNAUTHORIZED",
     *   "message": "Unauthorized - Use Basic Auth or JWT"
     * }
     */
    @And("Error response should be properly formatted with 401 status")
    public void verifyErrorResponseFormat() {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify all required error fields
        assertTrue(responseBody.contains("\"status\":401") || responseBody.contains("\"status\": 401"),
                "status field with value 401 not found in error response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"error\":\"UNAUTHORIZED\"") || 
                   responseBody.contains("\"error\": \"UNAUTHORIZED\""),
                "error field 'UNAUTHORIZED' not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"message\":"),
                "message field not found in error response. Actual response: " + responseBody);

        System.out.println("Error response is properly formatted with all required fields");
    }

    /**
     * Test Step: Verify plant was NOT created in the database
     * Since we got 401 Unauthorized, the plant should NOT exist in the system
     * This can be verified by checking the response - no plant ID is returned
     */
    @And("Plant {string} should NOT be created due to missing authentication")
public void verifyPlantNotCreatedUnauthenticated(String expectedPlantName) {
    String responseBody = plantsPageAPI.getResponseBody();

    // Verify response does NOT contain plant data (id, name, etc.)
    assertFalse(responseBody.contains("\"id\":") && responseBody.contains("\"name\":\"" + expectedPlantName + "\""),
            "Plant appears to have been created despite 401 Unauthorized. Response: " + responseBody);

    // Verify response is an error response (contains "error" field)
    assertTrue(responseBody.contains("\"error\":"),
            "Response does not appear to be an error response. Response: " + responseBody);

    System.out.println("Plant '" + expectedPlantName + "' was NOT created (as expected)");
    System.out.println("Authentication is properly enforced");
    System.out.println("Unauthenticated requests are blocked with 401 Unauthorized");
    System.out.println("Test Case API_Plant_UnauthorizedAccess_010 - PASSED");
}
}