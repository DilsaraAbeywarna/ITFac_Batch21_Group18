package starter.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import utils.TokenHolder;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import java.util.List;
import java.util.Map;

/**
 * Cucumber Hooks for setting up preconditions and cleanup
 * Handles authentication token generation and test data management
 */
public class Hooks {

    // Store created category IDs for cleanup
    private static ThreadLocal<List<Integer>> createdCategoryIds = ThreadLocal
            .withInitial(() -> new java.util.ArrayList<>());

    /**
     * Hook for @adminapi tagged scenarios
     */
    @Before("@adminapi")
    public void setupAdminToken() {
        System.out.println("🔐 Setting up Admin token for @adminapi scenario");

        Response response = SerenityRest.given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\", \"password\": \"admin123\" }")
                .post("/api/auth/login");

        String token = response.jsonPath().getString("token");
        TokenHolder.setToken(token);

        System.out.println("✅ Admin token generated successfully");
    }

    /**
     * Hook for @nonadminapi tagged scenarios
     */
    @Before("@nonadminapi")
    public void setupNonAdminToken() {
        System.out.println("🔐 Setting up User token for @nonadminapi scenario");

        Response response = SerenityRest.given()
                .contentType("application/json")
                .body("{ \"username\": \"testuser\", \"password\": \"test123\" }")
                .post("/api/auth/login");

        String token = response.jsonPath().getString("token");
        TokenHolder.setToken(token);

        System.out.println("✅ User token generated successfully");
    }

    /**
     * Setup test data BEFORE edit tests
     * Creates a category that can be edited/deleted
     */
    @Before("@api-edit-category or @api-delete-category")
    public void setupTestCategoryForEditOrDelete() {
        System.out.println("📝 Setting up test category for edit/delete scenario");

        try {
            // Login as admin
            Response loginResponse = SerenityRest.given()
                    .contentType("application/json")
                    .body("{ \"username\": \"admin\", \"password\": \"admin123\" }")
                    .post("/api/auth/login");

            String adminToken = loginResponse.jsonPath().getString("token");

            // Create a test category
            Response createResponse = SerenityRest.given()
                    .contentType("application/json")
                    .header("Authorization", "Bearer " + adminToken)
                    .body("{ \"name\": \"EditTest\" }")
                    .post("/api/categories");

            if (createResponse.statusCode() == 201) {
                Integer categoryId = createResponse.jsonPath().getInt("id");
                createdCategoryIds.get().add(categoryId);
                System.out.println("✅ Test category created with ID: " + categoryId);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Failed to setup test category: " + e.getMessage());
        }
    }

    /**
     * Cleanup hook for @api-add-category tagged scenarios
     */
    @After("@api-add-category")
    public void cleanupAddCategoryTests() {
        System.out.println("🧹 Cleaning up categories from add tests...");
        cleanupCategoriesByNames(new String[] { "Indoor", "Outdoor", "TestCat01", "Test_Cat" });
    }

    /**
     * Cleanup hook for @api-edit-category tagged scenarios
     */
    @After("@api-edit-category")
    public void cleanupEditCategoryTests() {
        System.out.println("🧹 Cleaning up categories from edit tests...");
        cleanupCategoriesByNames(new String[] { "EditTest", "Updated", "TestEdit" });
        cleanupStoredCategoryIds();
    }

    /**
     * Cleanup hook for @api-delete-category tagged scenarios
     */
    @After("@api-delete-category")
    public void cleanupDeleteCategoryTests() {
        System.out.println("🧹 Cleaning up categories from delete tests...");
        cleanupCategoriesByNames(new String[] { "DeleteTest", "ToDelete" });
        cleanupStoredCategoryIds();
    }

    /**
     * Cleanup hook for UI tests
     */
    @After("@ui")
    public void cleanupUITests() {
        System.out.println("🧹 Cleaning up categories from UI tests...");
        cleanupCategoriesByNames(new String[] { "Indoor", "Outdoor", "UITest", "TestCategory" });
    }

    /**
     * Global cleanup - runs after every scenario
     */
    @After
    public void globalCleanup(Scenario scenario) {
        System.out.println("🔓 Clearing token for scenario: " + scenario.getName());
        TokenHolder.clearToken();

        // Clear thread-local storage
        createdCategoryIds.get().clear();
    }

    // ==================== HELPER METHODS ====================

    /**
     * Delete categories by names
     */
    private void cleanupCategoriesByNames(String[] categoryNames) {
        try {
            String adminToken = getAdminToken();

            for (String categoryName : categoryNames) {
                deleteCategoryByName(categoryName, adminToken);
            }

            System.out.println("✅ Cleanup by names completed");

        } catch (Exception e) {
            System.out.println("⚠️ Cleanup warning: " + e.getMessage());
        }
    }

    /**
     * Delete categories by stored IDs
     */
    private void cleanupStoredCategoryIds() {
        try {
            String adminToken = getAdminToken();
            List<Integer> idsToDelete = createdCategoryIds.get();

            for (Integer categoryId : idsToDelete) {
                deleteCategoryById(categoryId, adminToken);
            }

            createdCategoryIds.get().clear();
            System.out.println("✅ Cleanup by IDs completed");

        } catch (Exception e) {
            System.out.println("⚠️ Cleanup warning: " + e.getMessage());
        }
    }

    /**
     * Get admin token for cleanup operations
     */
    private String getAdminToken() {
        Response loginResponse = SerenityRest.given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\", \"password\": \"admin123\" }")
                .post("/api/auth/login");

        return loginResponse.jsonPath().getString("token");
    }

    /**
     * Delete a category by name
     */
    private void deleteCategoryByName(String categoryName, String adminToken) {
        try {
            Response categoriesResponse = SerenityRest.given()
                    .header("Authorization", "Bearer " + adminToken)
                    .get("/api/categories");

            if (categoriesResponse.statusCode() == 200) {
                List<Map<String, Object>> categories = categoriesResponse.jsonPath().getList("$");

                for (Map<String, Object> category : categories) {
                    if (categoryName.equals(category.get("name"))) {
                        Object categoryId = category.get("id");
                        deleteCategoryById((Integer) categoryId, adminToken);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // Silently ignore - category might not exist
        }
    }

    /**
     * Delete a category by ID
     */
    private void deleteCategoryById(Integer categoryId, String adminToken) {
        try {
            Response deleteResponse = SerenityRest.given()
                    .header("Authorization", "Bearer " + adminToken)
                    .delete("/api/categories/" + categoryId);

            if (deleteResponse.statusCode() == 200 || deleteResponse.statusCode() == 204) {
                System.out.println("🗑️  Deleted category ID: " + categoryId);
            }
        } catch (Exception e) {
            // Silently ignore deletion errors
        }
    }

    /**
     * Public method to store category ID for cleanup
     */
    public static void storeCategoryIdForCleanup(Integer categoryId) {
        createdCategoryIds.get().add(categoryId);
    }

    /**
     * Public method to get a test category ID for edit/delete tests
     */
    public static Integer getTestCategoryId() {
        List<Integer> ids = createdCategoryIds.get();
        return ids.isEmpty() ? null : ids.get(0);
    }
}