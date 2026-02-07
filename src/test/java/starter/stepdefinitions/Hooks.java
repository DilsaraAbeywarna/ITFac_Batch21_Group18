package starter.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import utils.TokenHolder;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class Hooks {

    // ==================== CRITICAL: Browser Initialization ====================

    /**
     * HIGHEST PRIORITY: Initialize browser BEFORE any UI test
     * This runs FIRST (order=0) to ensure browser is ready
     */
    @Before(value = "@ui or @access-add or @access-edit or @add-category or @edit-category or @admin-login", order = 0)
    public void ensureBrowserIsInitialized() {
        System.out.println("🌐 [Browser Init] Ensuring WebDriver is ready for UI test...");

        try {
            // Force Serenity to initialize WebDriver
            WebDriver driver = ThucydidesWebDriverSupport.getWebdriverManager().getCurrentDriver();

            if (driver != null) {
                // Clear any previous session data
                driver.manage().deleteAllCookies();
                System.out.println("✅ [Browser Init] Browser ready and cookies cleared");
            } else {
                System.out.println("⚠️ [Browser Init] Driver was null, reinitializing...");
                ThucydidesWebDriverSupport.getWebdriverManager().closeCurrentDrivers();
                driver = ThucydidesWebDriverSupport.getWebdriverManager().getCurrentDriver();
                System.out.println("✅ [Browser Init] Browser reinitialized successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ [Browser Init] CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== API Token Setup ====================

    @Before(value = "@adminapi", order = 10)
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

    @Before(value = "@nonadminapi", order = 10)
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

    // ==================== Cleanup Hooks ====================

    @After(value = "@api-add-category", order = 50)
    public void cleanupAddCategoryTests() {
        System.out.println("🧹 Cleaning up categories from add tests...");
        cleanupCategoriesByNames(new String[] { "Indoor", "Outdoor", "TestCat01", "Test_Cat" });
    }

    @After(value = "@api-edit-category", order = 50)
    public void cleanupEditCategoryTests() {
        System.out.println("🧹 Cleaning up categories from edit tests...");
        cleanupCategoriesByNames(new String[] { "EditTest", "Updated", "TestEdit" });
    }

    @After(value = "@ui or @access-add or @access-edit or @add-category or @edit-category or @admin-login", order = 50)
    public void cleanupUITests() {
        System.out.println("🧹 Cleaning up categories from UI tests...");
        cleanupCategoriesByNames(new String[] { "Indoor", "Outdoor", "UITest", "TestCategory" });
    }

    /**
     * Close browser AFTER each UI test (order=100 runs last)
     */
    @After(value = "@ui or @access-add or @access-edit or @add-category or @edit-category or @admin-login", order = 100)
    public void closeBrowserAfterUITest() {
        System.out.println("🧹 [Browser Cleanup] Closing browser after UI test...");

        try {
            ThucydidesWebDriverSupport.getWebdriverManager().closeCurrentDrivers();
            System.out.println("✅ [Browser Cleanup] Browser closed successfully");
        } catch (Exception e) {
            System.out.println("⚠️ [Browser Cleanup] Warning: " + e.getMessage());
        }
    }

    /**
     * Global cleanup - runs for ALL scenarios
     */
    @After(order = 200)
    public void globalCleanup(Scenario scenario) {
        System.out.println("🔓 [Global Cleanup] Clearing token for: " + scenario.getName());
        TokenHolder.clearToken();
    }

    // ==================== Helper Methods ====================

    private void cleanupCategoriesByNames(String[] categoryNames) {
        try {
            String adminToken = getAdminToken();

            for (String categoryName : categoryNames) {
                deleteCategoryByName(categoryName, adminToken);
            }

            System.out.println("✅ Cleanup completed");

        } catch (Exception e) {
            System.out.println("⚠️ Cleanup warning: " + e.getMessage());
        }
    }

    private String getAdminToken() {
        Response loginResponse = SerenityRest.given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\", \"password\": \"admin123\" }")
                .post("/api/auth/login");

        return loginResponse.jsonPath().getString("token");
    }

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

                        Response deleteResponse = SerenityRest.given()
                                .header("Authorization", "Bearer " + adminToken)
                                .delete("/api/categories/" + categoryId);

                        if (deleteResponse.statusCode() == 200 || deleteResponse.statusCode() == 204) {
                            System.out.println("🗑️  Deleted category: " + categoryName);
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // Silently ignore
        }
    }
}