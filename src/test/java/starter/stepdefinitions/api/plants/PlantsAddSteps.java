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

public class PlantsAddSteps {

    @Steps
    PlantsPageAPI plantsPageAPI;

    private static final int TEST_CATEGORY_ID = 3;
    private static final int MAIN_CATEGORY_ID = 1;

    // ==================== TEST CASE: API_Plant_CreateValid_001 ====================
    // Verify successful plant creation by an Admin with valid data
    
    @When("Admin user sends POST request to create plant under sub-category")
    public void adminUserCreatesPlantUnderSubCategory() {
        Map<String, Object> plantBody = new HashMap<>();
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uniquePlantName = "Rose_" + timestamp;
        
        plantBody.put("name", uniquePlantName);
        plantBody.put("price", 75.50);
        plantBody.put("quantity", 20);
        
        plantsPageAPI.createPlantUnderCategory(TEST_CATEGORY_ID, plantBody);
        
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    @Then("API should return 201 Created status")
    public void verifyCreatedStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertEquals(201, actualStatus,
                "Expected status code 201 Created, but got: " + actualStatus +
                "\nResponse: " + responseBody);
    }

    @And("Response body should contain plant details with correct values")
    public void verifyPlantDetailsInResponse() {
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");
        
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
    }

    // ==================== TEST CASE: API_Plant_MissingNameError_002 ====================
    // Verify validation error when plant name is missing
    
    @When("Admin user sends POST request without plant name")
    public void adminUserSendsRequestWithoutPlantName() {
        Map<String, Object> plantBody = new HashMap<>();
        
        plantBody.put("price", 50.0);
        plantBody.put("quantity", 15);
        
        plantsPageAPI.createPlantUnderCategory(TEST_CATEGORY_ID, plantBody);
        
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    @Then("API should return 400 Bad Request status")
    public void verifyBadRequestStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertEquals(400, actualStatus,
                "Expected status code 400 Bad Request, but got: " + actualStatus +
                "\nResponse: " + responseBody);
    }

    @And("Response body should contain validation error for missing name")
    public void verifyValidationErrorForMissingName() {
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");
        
        assertTrue(responseBody.contains("\"status\":400"),
                "Status 400 not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"error\":\"BAD_REQUEST\""),
                "Error type 'BAD_REQUEST' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"message\":\"Validation failed\""),
                "Validation failed message not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"details\""),
                "Details object not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"name\":\"Plant name is required\""),
                "Validation error 'Plant name is required' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"timestamp\":"),
                "Timestamp not found in error response. Actual response: " + responseBody);
    }

    @And("Plant should not be created in database")
    public void verifyPlantNotCreatedInDatabase() {
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertFalse(responseBody.contains("\"id\":") && responseBody.contains("\"name\":\"Rose"),
                "Plant appears to have been created despite validation error. Response: " + responseBody);
    }

    // ==================== TEST CASE: API_Plant_NameLengthError_003 ====================
    // Verify validation error for plant name length less than 3 characters
    
    @When("Admin user sends POST request with plant name less than 3 characters")
    public void adminUserSendsRequestWithShortPlantName() {
        Map<String, Object> plantBody = new HashMap<>();
        
        plantBody.put("name", "AB");
        plantBody.put("price", 75.0);
        plantBody.put("quantity", 20);
        
        plantsPageAPI.createPlantUnderCategory(TEST_CATEGORY_ID, plantBody);
        
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    @And("Response body should contain validation error for name length")
    public void verifyValidationErrorForNameLength() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");
        
        assertTrue(responseBody.contains("\"status\":400"),
                "Status 400 not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"error\":\"BAD_REQUEST\""),
                "Error type 'BAD_REQUEST' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"message\":\"Validation failed\""),
                "Validation failed message not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"details\""),
                "Details object not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"name\":\"Plant name must be between 3 and 25 characters\""),
                "Validation error 'Plant name must be between 3 and 25 characters' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"timestamp\":"),
                "Timestamp not found in error response. Actual response: " + responseBody);
    }

    // ==================== TEST CASE: API_Plant_MainCategoryError_004 ====================
    // Verify error when category is main category (not sub-category)
    
    @When("Admin user sends POST request with main category ID")
    public void adminUserSendsRequestWithMainCategoryId() {
        Map<String, Object> plantBody = new HashMap<>();
        
        plantBody.put("name", "Lily");
        plantBody.put("price", 60.0);
        plantBody.put("quantity", 15);
        
        plantsPageAPI.createPlantUnderCategory(MAIN_CATEGORY_ID, plantBody);
        
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    @And("Response body should contain error for main category")
    public void verifyErrorForMainCategory() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();
        
        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");
        
        assertTrue(responseBody.contains("\"status\":400"),
                "Status 400 not found in error response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"error\":\"BAD_REQUEST\""),
                "Error type 'BAD_REQUEST' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"message\":\"Plants can only be added to sub-categories\""),
                "Error message 'Plants can only be added to sub-categories' not found in response. Actual response: " + responseBody);
        
        assertTrue(responseBody.contains("\"timestamp\":"),
                "Timestamp not found in error response. Actual response: " + responseBody);
    }
}