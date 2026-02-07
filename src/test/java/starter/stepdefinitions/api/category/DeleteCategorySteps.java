package starter.stepdefinitions.api.category;

import starter.api.category.AddEditCategoriesPageAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteCategorySteps {

    @Steps
    AddEditCategoriesPageAPI addEditCategoriesPageAPI;

    // Constants
    private static final String FIELD_NAME = "name";
    private static final String FIELD_ID = "id";

    // Dynamic category storage for delete operations
    private Integer categoryIdToDelete;
    private String categoryNameToDelete;

    // ==================== GIVEN STEPS ====================

    @Given("A category exists in the system for deletion")
    public void aCategoryExistsInTheSystemForDeletion() {
        Response response = addEditCategoriesPageAPI.getAllCategories();

        assertEquals(200, response.getStatusCode(),
                "Failed to retrieve categories. Status code: " + response.getStatusCode());

        List<Map<String, Object>> categories = response.jsonPath().getList("$");

        assertNotNull(categories, "Categories list should not be null");
        assertFalse(categories.isEmpty(),
                "No categories available in the system. Please create at least one category first.");

        // Get the first category
        Map<String, Object> firstCategory = categories.get(0);
        categoryIdToDelete = (Integer) firstCategory.get(FIELD_ID);
        categoryNameToDelete = (String) firstCategory.get(FIELD_NAME);

        assertNotNull(categoryIdToDelete, "Category ID should not be null");
        assertNotNull(categoryNameToDelete, "Category name should not be null");

        System.out.println("✓ Found category for deletion test:");
        System.out.println("  Category ID: " + categoryIdToDelete);
        System.out.println("  Category Name: " + categoryNameToDelete);
    }

    // ==================== WHEN STEPS ====================

    @When("Non-Admin user sends DELETE request to delete the category")
    public void nonAdminUserSendsDeleteRequestToDeleteTheCategory() {
        assertNotNull(categoryIdToDelete,
                "No category ID available. Ensure 'Given' step has been executed to fetch a category.");

        logCategoryDeletionRequest("Non-Admin", categoryIdToDelete, categoryNameToDelete);

        addEditCategoriesPageAPI.deleteCategory(categoryIdToDelete);

        assertNotNull(addEditCategoriesPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    // ==================== THEN STEPS ====================

    @Then("API should return {int} Forbidden status for category deletion")
    public void apiShouldReturnForbiddenStatusForCategoryDeletion(int expectedStatus) {
        verifyStatusCode(expectedStatus, "API_Category_DeleteCategoryUnauthorized_010");
    }

    // ==================== AND STEPS ====================

    @And("Category should not be deleted from the system")
    public void categoryShouldNotBeDeletedFromTheSystem() {
        Response verifyResponse = addEditCategoriesPageAPI.getAllCategories();

        assertEquals(200, verifyResponse.getStatusCode(),
                "Failed to retrieve categories for verification");

        List<Map<String, Object>> categories = verifyResponse.jsonPath().getList("$");

        // Find the category that was attempted to be deleted
        boolean categoryFound = false;
        for (Map<String, Object> category : categories) {
            Integer categoryId = (Integer) category.get(FIELD_ID);
            if (categoryId != null && categoryId.equals(categoryIdToDelete)) {
                String categoryName = (String) category.get(FIELD_NAME);

                assertEquals(categoryNameToDelete, categoryName,
                        "Category name changed despite unauthorized deletion attempt");

                categoryFound = true;
                System.out.println("✓ Category still exists in system (deletion prevented):");
                System.out.println("  Category ID: " + categoryId);
                System.out.println("  Category Name: " + categoryName);
                System.out.println("✓ Test Case API_Category_DeleteCategoryUnauthorized_010 - PASSED");
                break;
            }
        }

        assertTrue(categoryFound,
                "Category ID " + categoryIdToDelete
                        + " not found. It may have been deleted despite authorization failure.");
    }

    // ==================== HELPER METHODS ====================

    private void logCategoryDeletionRequest(String userType, int categoryId, String categoryName) {
        System.out.println("=".repeat(60));
        System.out.println("Deleting category as " + userType + " user");
        System.out.println("Category ID: " + categoryId);
        System.out.println("Category Name: '" + categoryName + "'");
        System.out.println("=".repeat(60));
    }

    private void verifyStatusCode(int expectedStatus, String testCaseId) {
        int actualStatus = addEditCategoriesPageAPI.getStatusCode();
        String responseBody = addEditCategoriesPageAPI.getResponseBody();

        System.out.println("=".repeat(60));
        System.out.println("API Test: " + testCaseId);
        System.out.println("Endpoint: DELETE /api/categories/{id}");
        System.out.println("Expected Status: " + expectedStatus);
        System.out.println("Actual Status: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("=".repeat(60));

        assertEquals(expectedStatus, actualStatus,
                String.format("Expected status code %d, but got: %d\nResponse: %s",
                        expectedStatus, actualStatus, responseBody));

        System.out.println("✓ Status code " + expectedStatus + " verified successfully");
    }
}