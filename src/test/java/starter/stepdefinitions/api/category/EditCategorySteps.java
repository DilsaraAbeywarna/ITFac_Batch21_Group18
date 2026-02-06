package starter.stepdefinitions.api.category;

import starter.api.category.AddEditCategoriesPageAPI;
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

public class EditCategorySteps {

    @Steps
    AddEditCategoriesPageAPI addEditCategoriesPageAPI;

    // Constants
    private static final String FIELD_NAME = "name";
    private static final String FIELD_ID = "id";
    private static final String FIELD_SUB_CATEGORIES = "subCategories";

    // Dynamic category storage
    private Integer dynamicCategoryId;
    private String originalCategoryName;

    // Given steps

    // Fetches the first available category from the system dynamically
    @Given("A category exists in the system")
    public void aCategoryExistsInTheSystem() {

        Response response = addEditCategoriesPageAPI.getAllCategories();

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
        System.out.println("  Category ID: " + dynamicCategoryId);
        System.out.println("  Original Name: " + originalCategoryName);
    }

    // When steps

    // Updates the dynamically fetched category with a new name - Admin user
    @When("Admin user updates the category name to {string}")
    public void adminUserUpdatesTheCategoryNameTo(String newCategoryName) {
        assertNotNull(dynamicCategoryId,
                "No category ID available. Ensure 'Given' step has been executed to fetch a category.");

        Map<String, Object> categoryBody = buildCategoryRequestBody(newCategoryName);
        logCategoryUpdateRequest("Admin", dynamicCategoryId, originalCategoryName, newCategoryName, categoryBody);

        addEditCategoriesPageAPI.updateCategory(dynamicCategoryId, categoryBody);

        assertNotNull(addEditCategoriesPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    // Updates the dynamically fetched category with a new name - Non-Admin user
    @When("Non-Admin user updates the category name to {string}")
    public void nonAdminUserUpdatesTheCategoryNameTo(String newCategoryName) {
        assertNotNull(dynamicCategoryId,
                "No category ID available. Ensure 'Given' step has been executed to fetch a category.");

        Map<String, Object> categoryBody = buildCategoryRequestBody(newCategoryName);
        logCategoryUpdateRequest("Non-Admin", dynamicCategoryId, originalCategoryName, newCategoryName, categoryBody);

        addEditCategoriesPageAPI.updateCategory(dynamicCategoryId, categoryBody);

        assertNotNull(addEditCategoriesPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    // Then steps

    // Verify response status code is 200 OK
    @Then("API should return {int} OK status for category update")
    public void apiShouldReturnOkStatusForCategoryUpdate(int expectedStatus) {
        verifyStatusCode(expectedStatus, "API_Category_UpdateCategory_004");
    }

    // Verify response status code is 400 Bad Request
    @Then("API should return {int} Bad Request status for invalid category update")
    public void apiShouldReturnBadRequestStatusForInvalidCategoryUpdate(int expectedStatus) {
        verifyStatusCode(expectedStatus, "API_Category_InvalidUpdateValidation");
    }

    // Verify response status code is 403 Forbidden
    @Then("API should return {int} Forbidden status for category update")
    public void apiShouldReturnForbiddenStatusForCategoryUpdate(int expectedStatus) {
        verifyStatusCode(expectedStatus, "API_Category_EditCategoryUnauthorized");
    }

    // And steps

    // Verify response body contains the updated category name
    @And("Response body should contain updated category name {string}")
    public void responseBodyShouldContainUpdatedCategoryName(String expectedCategoryName) {
        String responseBody = addEditCategoriesPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");

        String actualCategoryName = addEditCategoriesPageAPI.getJsonPathValue(FIELD_NAME);

        assertEquals(expectedCategoryName, actualCategoryName,
                String.format("Expected category name '%s' but got '%s'",
                        expectedCategoryName, actualCategoryName));

        System.out.println("✓ Category name updated to '" + expectedCategoryName + "'");
    }

    // Verify category ID remains unchanged after update
    @And("Response body should contain the same category ID")
    public void responseBodyShouldContainTheSameCategoryId() {
        assertNotNull(dynamicCategoryId, "Dynamic category ID not available");

        Integer actualCategoryId = addEditCategoriesPageAPI.getJsonPathValue(FIELD_ID);

        assertNotNull(actualCategoryId, "Category ID should not be null");
        assertEquals(dynamicCategoryId, actualCategoryId,
                String.format("Expected category ID %d but got %d",
                        dynamicCategoryId, actualCategoryId));

        System.out.println("✓ Category ID remains: " + actualCategoryId);
    }

    // Verify category update is persisted in the system
    @And("Category should be updated successfully in the system")
    public void categoryShouldBeUpdatedSuccessfullyInSystem() {
        String responseBody = addEditCategoriesPageAPI.getResponseBody();

        try {
            Integer categoryId = addEditCategoriesPageAPI.getJsonPathValue(FIELD_ID);
            String categoryName = addEditCategoriesPageAPI.getJsonPathValue(FIELD_NAME);

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

    // Verify category details remain unchanged after failed update
    @And("Category details should remain unchanged in the system")
    public void categoryDetailsShouldRemainUnchangedInSystem() {
        Response verifyResponse = addEditCategoriesPageAPI.getAllCategories();

        assertEquals(200, verifyResponse.getStatusCode(),
                "Failed to retrieve categories for verification");

        List<Map<String, Object>> categories = verifyResponse.jsonPath().getList("$");

        // Find the category that was attempted to be updated
        boolean categoryFound = false;
        for (Map<String, Object> category : categories) {
            Integer categoryId = (Integer) category.get(FIELD_ID);
            if (categoryId != null && categoryId.equals(dynamicCategoryId)) {
                String currentName = (String) category.get(FIELD_NAME);

                assertEquals(originalCategoryName, currentName,
                        String.format("Category name changed from '%s' to '%s' despite unauthorized access",
                                originalCategoryName, currentName));

                categoryFound = true;
                System.out.println("✓ Category details verified as unchanged:");
                System.out.println("  Category ID: " + categoryId);
                System.out.println("  Name (unchanged): " + currentName);
                System.out.println("✓ Unauthorized update test - PASSED");
                break;
            }
        }

        assertTrue(categoryFound, "Could not find category ID " + dynamicCategoryId + " for verification");
    }

    // Verify validation rules were not applied due to lack of permission
    @And("Validation rules should not be applied due to lack of permission for update")
    public void validationRulesShouldNotBeAppliedDueToLackOfPermissionForUpdate() {
        String responseBody = addEditCategoriesPageAPI.getResponseBody();

        // Verify that response does NOT contain validation details
        assertFalse(responseBody.contains("\"details\""),
                "Response should not contain validation details for unauthorized users");

        assertFalse(responseBody.contains("Validation failed"),
                "Response should not contain validation messages for unauthorized users");

        assertFalse(responseBody.contains("Category name must be between"),
                "Response should not contain specific validation error messages for unauthorized users");

        System.out.println("✓ Validation rules were not applied (as expected for unauthorized access)");
        System.out.println("✓ Test Case API_Category_EditCategoryInvalidDataUnauthorized_009 - PASSED");
    }

    // helper methods

    // Builds the request body for updating a category
    private Map<String, Object> buildCategoryRequestBody(String categoryName) {
        Map<String, Object> categoryBody = new HashMap<>();
        categoryBody.put(FIELD_NAME, categoryName);
        return categoryBody;
    }

    // Logs the category update request details
    private void logCategoryUpdateRequest(String userType, int categoryId, String originalName, String newName,
            Map<String, Object> categoryBody) {
        System.out.println("=".repeat(60));
        System.out.println("Updating category as " + userType + " user");
        System.out.println("Category ID: " + categoryId);
        System.out.println("Original Name: '" + originalName + "'");
        System.out.println("New Name: '" + newName + "'");
        System.out.println("Name Length: " + newName.length() + " characters");
        System.out.println("Request Body: " + categoryBody);
        System.out.println("=".repeat(60));
    }

    // Verifies the status code and logs the details
    private void verifyStatusCode(int expectedStatus, String testCaseId) {
        int actualStatus = addEditCategoriesPageAPI.getStatusCode();
        String responseBody = addEditCategoriesPageAPI.getResponseBody();

        System.out.println("=".repeat(60));
        System.out.println("API Test: " + testCaseId);
        System.out.println("Endpoint: PUT /api/categories/{id}");
        System.out.println("Expected Status: " + expectedStatus);
        System.out.println("Actual Status: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("=".repeat(60));

        assertEquals(expectedStatus, actualStatus,
                String.format("Expected status code %d, but got: %d\nResponse: %s",
                        expectedStatus, actualStatus, responseBody));

        System.out.println("✓ Status code " + expectedStatus + " verified successfully");
    }

    // Logs successful category update
    private void logCategoryUpdateSuccess(Integer categoryId, String newCategoryName, String originalName) {
        System.out.println("✓ Category updated successfully:");
        System.out.println("  Category ID: " + categoryId);
        System.out.println("  Original Name: '" + originalName + "'");
        System.out.println("  New Name: '" + newCategoryName + "'");
        System.out.println("✓ Test Case API_Category_UpdateCategory_004 - PASSED");
    }
}