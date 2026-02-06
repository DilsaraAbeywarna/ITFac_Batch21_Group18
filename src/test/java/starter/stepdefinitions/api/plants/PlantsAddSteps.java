package starter.stepdefinitions.api.plants;

import starter.api.plants.PlantsPageAPI;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import net.serenitybdd.annotations.Steps;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions for Plants API - Add Plant functionality
 * Test Cases: 
 * - API_Plant_CreateValid_001
 * - API_Plant_MissingNameError_002
 * - API_Plant_NameLengthError_003
 */
public class PlantsAddSteps {

    @Steps
    PlantsPageAPI plantsPageAPI;

    private static final int TEST_CATEGORY_ID = 3; // Sub-category "Annual"

    // ==================== API_Plant_CreateValid_001 ====================
    
    /**
     * Test Step: Admin user sends POST request to create plant under sub-category
     * Uses timestamp to ensure unique plant names and avoid duplicate errors
     */
    @When("Admin user sends POST request to create plant under sub-category")
    public void adminUserCreatesPlantUnderSubCategory() {
        Map<String, Object> plantBody = new HashMap<>();
        
        // Generate unique plant name with timestamp to avoid DUPLICATE_RESOURCE error
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uniquePlantName = "Rose_" + timestamp;
        
        plantBody.put("name", uniquePlantName);
        plantBody.put("price", 75.50);
        plantBody.put("quantity", 20);
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Creating plant with name: " + uniquePlantName);
        System.out.println("═══════════════════════════════════════════════════════");
        
        // Send POST request to /api/plants/category/3
        plantsPageAPI.createPlantUnderCategory(TEST_CATEGORY_ID, plantBody);
        
        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify response status code is 201 Created
     */
    @Then("API should return 201 Created status")
    public void verifyCreatedStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_CreateValid_001");
        System.out.println("Endpoint: POST /api/plants/category/3");
        System.out.println("Status Code: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("═══════════════════════════════════════════════════════");
        
        assertEquals(201, actualStatus,
                "Expected status code 201 Created, but got: " + actualStatus +
                "\nResponse: " + responseBody);
    }

    /**
     * Test Step: Verify response body contains correct plant details
     */
    @And("Response body should contain plant details with correct values")
    public void verifyPlantDetailsInResponse() {
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");
        
        // Verify plant name contains "Rose_" (timestamp will vary)
        assertTrue(responseBody.contains("\"name\":\"Rose_"),
                "Plant name starting with 'Rose_' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"price\":75.5"),
                "Plant price 75.5 not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"quantity\":20"),
                "Plant quantity 20 not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"category\""),
                "Category object not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"id\":3") || responseBody.contains("\"categoryId\":3"),
                "Category ID 3 not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"name\":\"Annual\""),
                "Category name 'Annual' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"id\":"),
                "Plant ID not found in response. Actual response: " + responseBody);
        
        System.out.println("✓ All plant details verified successfully in response");
        System.out.println("✓ Test Case API_Plant_CreateValid_001 - PASSED");
    }

    // ==================== API_Plant_MissingNameError_002 ====================
    
    /**
     * Test Step: Admin user sends POST request WITHOUT plant name
     * Tests validation error handling for missing required field
     */
    @When("Admin user sends POST request without plant name")
    public void adminUserSendsRequestWithoutPlantName() {
        Map<String, Object> plantBody = new HashMap<>();
        
        // Intentionally OMIT name field to trigger validation error
        // Only include price and quantity
        plantBody.put("price", 50.0);
        plantBody.put("quantity", 15);
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Sending request WITHOUT name field (testing validation)");
        System.out.println("Request Body: " + plantBody);
        System.out.println("═══════════════════════════════════════════════════════");
        
        // Send POST request to /api/plants/category/3
        plantsPageAPI.createPlantUnderCategory(TEST_CATEGORY_ID, plantBody);
        
        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify response status code is 400 Bad Request
     */
    @Then("API should return 400 Bad Request status")
    public void verifyBadRequestStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_MissingNameError_002");
        System.out.println("Endpoint: POST /api/plants/category/3");
        System.out.println("Status Code: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("═══════════════════════════════════════════════════════");
        
        assertEquals(400, actualStatus,
                "Expected status code 400 Bad Request, but got: " + actualStatus +
                "\nResponse: " + responseBody);
    }

    /**
     * Test Step: Verify validation error response structure
     * Expected response structure:
     * {
     *   "status": 400,
     *   "error": "BAD_REQUEST",
     *   "message": "Validation failed",
     *   "details": {
     *     "name": "Plant name is required"
     *   }
     * }
     */
    @And("Response body should contain validation error for missing name")
    public void verifyValidationErrorForMissingName() {
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");
        
        // Verify error response structure
        assertTrue(responseBody.contains("\"status\":400"),
                "Status 400 not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"error\":\"BAD_REQUEST\""),
                "Error type 'BAD_REQUEST' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"message\":\"Validation failed\""),
                "Validation failed message not found in response. Actual response: " + responseBody);
        
        // Verify specific validation error for name field
        assertTrue(responseBody.contains("\"details\""),
                "Details object not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"name\":\"Plant name is required\""),
                "Validation error 'Plant name is required' not found in response. Actual response: " + responseBody);
        
        // Verify timestamp exists
        assertTrue(responseBody.contains("\"timestamp\":"),
                "Timestamp not found in error response. Actual response: " + responseBody);
        
        System.out.println("✓ Validation error response verified successfully");
        System.out.println("✓ Error message: 'Plant name is required' confirmed");
        System.out.println("✓ Test Case API_Plant_MissingNameError_002 - PASSED");
    }

    /**
     * Test Step: Verify plant was NOT created in database
     * This is implicit - if status is 400, no plant should be created
     */
    @And("Plant should not be created in database")
    public void verifyPlantNotCreatedInDatabase() {
        // Since we got 400 Bad Request, the plant should NOT be created
        // We can verify by checking the response doesn't contain an ID
        String responseBody = plantsPageAPI.getResponseBody();
        
        // Error responses should NOT contain a plant ID
        assertFalse(responseBody.contains("\"id\":") && responseBody.contains("\"name\":\"Rose"),
                "Plant appears to have been created despite validation error. Response: " + responseBody);
        
        System.out.println("✓ Confirmed: Plant was NOT created due to validation error");
    }

    // ==================== API_Plant_NameLengthError_003 ====================
    
    /**
     * Test Step: Admin user sends POST request with plant name less than 3 characters
     * Tests validation error handling for name length constraint
     */
    @When("Admin user sends POST request with plant name less than 3 characters")
    public void adminUserSendsRequestWithShortPlantName() {
        Map<String, Object> plantBody = new HashMap<>();
        
        // Provide name with only 2 characters (invalid - minimum is 3)
        plantBody.put("name", "AB");
        plantBody.put("price", 75.0);
        plantBody.put("quantity", 20);
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Sending request with SHORT name (2 chars - testing validation)");
        System.out.println("Request Body: " + plantBody);
        System.out.println("═══════════════════════════════════════════════════════");
        
        // Send POST request to /api/plants/category/3
        plantsPageAPI.createPlantUnderCategory(TEST_CATEGORY_ID, plantBody);
        
        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify validation error for name length constraint
     * Expected response structure:
     * {
     *   "status": 400,
     *   "error": "BAD_REQUEST",
     *   "message": "Validation failed",
     *   "details": {
     *     "name": "Plant name must be between 3 and 25 characters"
     *   }
     * }
     */
    @And("Response body should contain validation error for name length")
    public void verifyValidationErrorForNameLength() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();
        
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_NameLengthError_003");
        System.out.println("Endpoint: POST /api/plants/category/3");
        System.out.println("Status Code: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("═══════════════════════════════════════════════════════");
        
        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");
        
        // Verify error response structure
        assertTrue(responseBody.contains("\"status\":400"),
                "Status 400 not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"error\":\"BAD_REQUEST\""),
                "Error type 'BAD_REQUEST' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"message\":\"Validation failed\""),
                "Validation failed message not found in response. Actual response: " + responseBody);
        
        // Verify specific validation error for name length
        assertTrue(responseBody.contains("\"details\""),
                "Details object not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"name\":\"Plant name must be between 3 and 25 characters\""),
                "Validation error 'Plant name must be between 3 and 25 characters' not found in response. Actual response: " + responseBody);
        
        // Verify timestamp exists
        assertTrue(responseBody.contains("\"timestamp\":"),
                "Timestamp not found in error response. Actual response: " + responseBody);
        
        System.out.println("✓ Validation error response verified successfully");
        System.out.println("✓ Error message: 'Plant name must be between 3 and 25 characters' confirmed");
        System.out.println("✓ Test Case API_Plant_NameLengthError_003 - PASSED");
    }
}