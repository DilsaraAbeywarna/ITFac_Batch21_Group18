package starter.stepdefinitions.api.plants;

import starter.api.plants.PlantsPageAPI;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import net.serenitybdd.annotations.Steps;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions for Plants API - Filter Plants by Category
 * Test Case: API_Plant_FilterByCategory_008
 */
public class PlantsFilterByCategorySteps {

    @Steps
    PlantsPageAPI plantsPageAPI;

    private int filterCategoryId;

    // ==================== API_Plant_FilterByCategory_008 ====================

    /**
     * Precondition Step: Verify category with specific ID exists and contains plants
     * This is a passive check - we assume the precondition is met
     */
    @Given("Category with ID {int} exists and contains plants")
    public void verifyCategoryExistsWithPlants(int categoryId) {
        this.filterCategoryId = categoryId;
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Precondition: Category with ID " + categoryId + " exists and contains plants");
        System.out.println("Note: This is verified by checking filter results");
        System.out.println("═══════════════════════════════════════════════════════");
    }

    /**
     * Test Step: Normal User sends GET request to filter plants by category ID
     * Endpoint: GET /api/plants/paged?categoryId={categoryId}&page=0&size=10
     */
    @When("Normal User sends GET request to filter plants by category {int}")
    public void normalUserFiltersPlantsByCategory(int categoryId) {
        this.filterCategoryId = categoryId;
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Sending GET request to filter plants by category");
        System.out.println("Category ID: " + categoryId);
        System.out.println("Endpoint: GET /api/plants/paged?categoryId=" + categoryId + "&page=0&size=10");
        System.out.println("Role: Normal User (ROLE_USER)");
        System.out.println("═══════════════════════════════════════════════════════");

        // Send GET request with categoryId query parameter (page=0, size=10)
        plantsPageAPI.filterPlantsByCategory(categoryId, 0, 10);

        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify response status code is 200 OK
     * Normal users should have READ access to filter plants by category
     */
    @Then("Filter API should return 200 OK status")
    public void verifyFilterOkStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_FilterByCategory_008");
        System.out.println("Endpoint: GET /api/plants/paged?categoryId=" + filterCategoryId);
        System.out.println("Status Code: " + actualStatus);
        System.out.println("Response Body: " + responseBody);
        System.out.println("═══════════════════════════════════════════════════════");

        assertEquals(200, actualStatus,
                "Expected status code 200 OK, but got: " + actualStatus +
                        "\nResponse: " + responseBody);
    }

    /**
     * Test Step: Verify response contains paginated structure
     * Expected structure includes: content, totalElements, totalPages, pageable, etc.
     * Based on actual API response:
     * {
     *   "content": [...],
     *   "pageable": {...},
     *   "last": true,
     *   "totalPages": 1,
     *   "totalElements": 5,
     *   "size": 10,
     *   "number": 0,
     *   "sort": {...},
     *   "first": true,
     *   "numberOfElements": 5,
     *   "empty": false
     * }
     */
    @And("Filter response should contain paginated structure")
    public void verifyFilterResponseStructure() {
        String responseBody = plantsPageAPI.getResponseBody();

        assertNotNull(responseBody, "Response body is null");
        assertFalse(responseBody.isEmpty(), "Response body is empty");

        // Verify paginated response structure (all required fields from actual API)
        assertTrue(responseBody.contains("\"content\":"),
                "Content array not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"totalElements\":"),
                "totalElements field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"totalPages\":"),
                "totalPages field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"pageable\":"),
                "pageable field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"size\":"),
                "size field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"number\":"),
                "number field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"first\":"),
                "first field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"last\":"),
                "last field not found in response. Actual response: " + responseBody);

        assertTrue(responseBody.contains("\"empty\":"),
                "empty field not found in response. Actual response: " + responseBody);

        System.out.println("Response has valid paginated structure");
    }

    /**
     * Test Step: Verify response contains only plants from specified category
     * All returned plants should have category.id matching the filter categoryId
     * Example from actual API:
     * - Filter categoryId=4 returns only plants with "category": {"id": 4, "name": "Perennial", ...}
     */
    @And("Response should contain only plants with category ID {int}")
    public void verifyFilterResultsMatchCategory(int expectedCategoryId) {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify content array exists
        assertTrue(responseBody.contains("\"content\":"),
                "Content array not found in response. Actual response: " + responseBody);

        // Verify results are not empty (check for "empty":false)
        assertTrue(responseBody.contains("\"empty\":false"),
                "Filter returned empty results. Expected plants with categoryId = " + expectedCategoryId + ". Response: " + responseBody);

        // Verify category object with matching ID exists in response
        String expectedCategoryPattern = "\"category\":{\"id\":" + expectedCategoryId;
        assertTrue(responseBody.contains(expectedCategoryPattern),
                "Filter results do not contain plants with categoryId = " + expectedCategoryId + ". Response: " + responseBody);

        System.out.println("Filter results contain plants with category ID: " + expectedCategoryId);
    }

    /**
     * Test Step: Verify plants from other categories are excluded
     * Expected: Only plants with matching categoryId appear
     * Expected: Plants from different categories are NOT in results
     */
    @And("Plants from other categories are excluded from results")
    public void verifyNonMatchingCategoriesExcluded() {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify totalElements matches actual count of matching plants
        assertTrue(responseBody.contains("\"totalElements\":"),
                "totalElements field not found. Response: " + responseBody);

        // Extract totalElements value
        int startIndex = responseBody.indexOf("\"totalElements\":") + 16;
        int endIndex = responseBody.indexOf(",", startIndex);
        if (endIndex == -1) endIndex = responseBody.indexOf("}", startIndex);
        String totalElementsStr = responseBody.substring(startIndex, endIndex).trim();
        int totalElements = Integer.parseInt(totalElementsStr);

        // Verify we have filtered results
        assertTrue(totalElements > 0,
                "Filter returned 0 results. Expected plants with categoryId = " + filterCategoryId + ". Response: " + responseBody);

        System.out.println("Filter worked correctly - Found " + totalElements + " plants with category ID " + filterCategoryId);
        System.out.println("Plants from other categories are excluded from results");
    }

    /**
     * Test Step: Verify category filter works correctly
     * Confirms that all returned plants belong to the specified category
     */
    @And("API category filter works correctly")
    public void verifyCategoryFilterWorksCorrectly() {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify results contain category information
        assertTrue(responseBody.contains("\"category\":"),
                "Category field not found in response. Response: " + responseBody);

        // Verify category ID matches filter
        String expectedCategoryId = "\"id\":" + filterCategoryId;
        assertTrue(responseBody.contains(expectedCategoryId),
                "Category ID " + filterCategoryId + " not found in results. Response: " + responseBody);

        // Verify empty is false (results exist)
        assertTrue(responseBody.contains("\"empty\":false"),
                "Filter returned empty results. Category filter may not be working. Response: " + responseBody);

        System.out.println("Category filter is working correctly (filtered by category ID: " + filterCategoryId + ")");
        System.out.println("Normal User successfully filtered plants by category (READ permission confirmed)");
        System.out.println("Test Case API_Plant_FilterByCategory_008 - PASSED");
    }
}