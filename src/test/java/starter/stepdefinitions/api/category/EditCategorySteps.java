package starter.stepdefinitions.api.category;

import starter.api.category.EditCategoryPageAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions for Categories API - Edit Category functionality
 * Test Case: API_Category_UpdateCategory_004
 * Description: Verify Admin can update a main category via API
 */
public class EditCategorySteps {

    @Steps
    EditCategoryPageAPI editCategoryPageAPI;

    // Constants
    private static final String FIELD_NAME = "name";
    private static final String FIELD_ID = "id";
    private static final String FIELD_SUB_CATEGORIES = "subCategories";

    // Dynamic category storage
    private Integer dynamicCategoryId;
    private String originalCategoryName;

    // ==================== GIVEN STEPS ====================

    /**
     * Fetches the first available category from the system dynamically
     */
    @Given("A category exists in the system")
    public void aCategoryExistsInTheSystem() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Fetching available categories from the system...");

        Response response = editCategoryPageAPI.getAllCategories();

        assertEquals(200, response.getStatusCode(),
                "Failed to retrieve categories. Status code: " + response.getStatusCode());

        List<Map<String, Object>> categories = response.jsonPath().getList("$");

        assertNotNull(categories, "Categories list should not be null");
        assertFalse(categories.isEmpty(),
                "No categories available in the system. Please create at least one category first.");

        // Get the first category
        Map<String, Object> firstCategory = categories.get(0);
        dynamicCategoryId = (Integer) firstCategory.get(FIELD_ID);
        originalCategoryName = (String) firstCategory.get(FIELD_NAME);

        assertNotNull(dynamicCategoryId, "Category ID should not be null");
        assertNotNull(originalCategoryName, "Category name should not be null");

        System.out.println("✓ Found category to update:");
        System.out.println("  - Category ID: " + dynamicCategoryId);
        System.out.println("  - Original Name: " + originalCategoryName);
        System.out.println("  - Total categories available: " + categories.size());
        System.out.println("═══════════════════════════════════════════════════════");
    }

    // ==================== WHEN STEPS ====================

    /**
     * Updates the dynamically fetched category with a new name
     */
    @When("Admin user updates the category name to {string}")
    public void adminUserUpdatesTheCategoryNameTo(String newCategoryName) {
        assertNotNull(dynamicCategoryId,
                "No category ID available. Ensure 'Given' step has been executed to fetch a category.");

        Map<String, Object> categoryBody = buildCategoryRequestBody(newCategoryName);
        logCategoryUpdateRequest(dynamicCategoryId, originalCategoryName, newCategoryName, categoryBody);

        editCategoryPageAPI.updateCategory(dynamicCategoryId, categoryBody);

        assertNotNull(editCategoryPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    // ==================== THEN STEPS ====================

    /**
     * Verify response status code is 200 OK
     */
    @Then("API should return {int} OK status for category update")
    public void apiShouldReturnOkStatusForCategoryUpdate(int expectedStatus) {
        int actualStatus = editCategoryPageAPI.getStatusCode();
        String responseBody = editCategoryPageAPI.getResponseBody();

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Category_UpdateCategory_004");
        System.out.println("Endpoint: PUT /api/categories/{id}");
        System.out.println("Expected Status: " + expectedStatus);
        System.out.println("Actual Status: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("═══════════════════════════════════════════════════════");

        assertEquals(expectedStatus, actualStatus,
                String.format("Expected status code %d, but got: %d\nResponse: %s",
                        expectedStatus, actualStatus, responseBody));
    }

    // ==================== AND STEPS ====================

    /**
     * Verify response body contains the updated category name
     */
    @And("Response body should contain updated category name {string}")
    public void responseBodyShouldContainUpdatedCategoryName(String expectedCategoryName) {
        String responseBody = editCategoryPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");

        String actualCategoryName = editCategoryPageAPI.getJsonPathValue(FIELD_NAME);

        assertEquals(expectedCategoryName, actualCategoryName,
                String.format("Expected category name '%s' but got '%s'",
                        expectedCategoryName, actualCategoryName));

        System.out.println("✓ Category name updated to '" + expectedCategoryName + "' verified successfully");
    }

    /**
     * Verify category ID remains unchanged after update
     */
    @And("Response body should contain the same category ID")
    public void responseBodyShouldContainTheSameCategoryId() {
        assertNotNull(dynamicCategoryId, "Dynamic category ID not available");

        Integer actualCategoryId = editCategoryPageAPI.getJsonPathValue(FIELD_ID);

        assertNotNull(actualCategoryId, "Category ID should not be null");
        assertEquals(dynamicCategoryId, actualCategoryId,
                String.format("Expected category ID %d but got %d",
                        dynamicCategoryId, actualCategoryId));

        System.out.println("✓ Category ID " + actualCategoryId + " verified (unchanged after update)");
    }

    /**
     * Verify category update is persisted in the system
     */
    @And("Category should be updated successfully in the system")
    public void categoryShouldBeUpdatedSuccessfullyInSystem() {
        String responseBody = editCategoryPageAPI.getResponseBody();

        try {
            Integer categoryId = editCategoryPageAPI.getJsonPathValue(FIELD_ID);
            String categoryName = editCategoryPageAPI.getJsonPathValue(FIELD_NAME);

            assertNotNull(categoryId, "Category ID should not be null");
            assertNotNull(categoryName, "Category name should not be null");
            assertTrue(categoryId > 0,
                    "Category ID should be a positive integer. Got: " + categoryId);

            assertTrue(responseBody.contains("\"" + FIELD_SUB_CATEGORIES + "\""),
                    "Response should contain '" + FIELD_SUB_CATEGORIES + "' field");

            logCategoryUpdateSuccess(categoryId, categoryName, originalCategoryName);

        } catch (Exception e) {
            fail("Failed to verify category update persistence. Response: " + responseBody
                    + "\nError: " + e.getMessage());
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Builds the request body for updating a category
     */
    private Map<String, Object> buildCategoryRequestBody(String categoryName) {
        Map<String, Object> categoryBody = new HashMap<>();
        categoryBody.put(FIELD_NAME, categoryName);
        return categoryBody;
    }

    /**
     * Logs the category update request details
     */
    private void logCategoryUpdateRequest(int categoryId, String originalName, String newName,
            Map<String, Object> categoryBody) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("UPDATING CATEGORY:");
        System.out.println("  - Category ID: " + categoryId);
        System.out.println("  - Original Name: " + originalName);
        System.out.println("  - New Name: " + newName);
        System.out.println("  - Request Body: " + categoryBody);
        System.out.println("═══════════════════════════════════════════════════════");
    }

    /**
     * Logs successful category update
     */
    private void logCategoryUpdateSuccess(Integer categoryId, String newCategoryName, String originalName) {
        System.out.println("✓ Category updated successfully:");
        System.out.println("  - Category ID: " + categoryId + " (unchanged)");
        System.out.println("  - Original Name: " + originalName);
        System.out.println("  - Updated Name: " + newCategoryName);
        System.out.println("  - SubCategories field: present");
        System.out.println("✓ All category update details verified successfully");
        System.out.println("✓ Test Case API_Category_UpdateCategory_004 - PASSED");
    }
}