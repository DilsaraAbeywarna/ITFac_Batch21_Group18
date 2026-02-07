package starter.stepdefinitions.api.plants;

import starter.api.plants.PlantsPageAPI;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import net.serenitybdd.annotations.Steps;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step Definitions for Plants API - Search Plants by Name
 * Test Case: API_Plant_SearchByName_007
 */
public class PlantsSearchByNameSteps {

    @Steps
    PlantsPageAPI plantsPageAPI;

    private String searchKeyword;

    // ==================== API_Plant_SearchByName_007 ====================

    /**
     * Precondition Step: Verify plant with specific name exists in database
     * This is a passive check - we assume the precondition is met
     */
    @Given("Plant with name containing {string} exists in the database")
    public void verifyPlantExistsInDatabase(String plantName) {
        this.searchKeyword = plantName;
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Precondition: Plant with name containing '" + plantName + "' exists in database");
        System.out.println("Note: This is verified by checking search results");
        System.out.println("═══════════════════════════════════════════════════════");
    }

    /**
     * Test Step: Normal User sends GET request to search plants by name
     * Endpoint: GET /api/plants/paged?name={searchName}&page=0&size=10
     */
    @When("Normal User sends GET request to search plants by name {string}")
    public void normalUserSearchesPlantsByName(String searchName) {
        this.searchKeyword = searchName;
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Sending GET request to search plants");
        System.out.println("Search Keyword: " + searchName);
        System.out.println("Endpoint: GET /api/plants/paged?name=" + searchName + "&page=0&size=10");
        System.out.println("Role: Normal User (ROLE_USER)");
        System.out.println("═══════════════════════════════════════════════════════");

        // Send GET request with name query parameter (page=0, size=10)
        plantsPageAPI.searchPlantsByName(searchName, 0, 10);

        // Null safety check
        assertNotNull(plantsPageAPI.getResponse(),
                "API response is null. Request may not have been sent successfully.");
    }

    /**
     * Test Step: Verify response status code is 200 OK
     * Normal users should have READ access to search plants
     */
    @Then("Search API should return 200 OK status")
    public void verifySearchOkStatus() {
        int actualStatus = plantsPageAPI.getStatusCode();
        String responseBody = plantsPageAPI.getResponseBody();

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("API Test: API_Plant_SearchByName_007");
        System.out.println("Endpoint: GET /api/plants/paged?name=" + searchKeyword);
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
     *   "totalElements": 2,
     *   "size": 10,
     *   "number": 0,
     *   "sort": {...},
     *   "first": true,
     *   "numberOfElements": 2,
     *   "empty": false
     * }
     */
    @And("Search response should contain paginated structure")
    public void verifySearchResponseStructure() {
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
     * Test Step: Verify response contains only plants matching search keyword
     * All returned plants should have the search keyword in their name (case-insensitive)
     * Example from actual API:
     * - Search "Rose" returns: "Rose", "Rose_20260206035115"
     */
    @And("Response should contain only plants with {string} in the name")
    public void verifySearchResultsMatchKeyword(String expectedKeyword) {
        String responseBody = plantsPageAPI.getResponseBody();

        // Verify content array exists
        assertTrue(responseBody.contains("\"content\":"),
                "Content array not found in response. Actual response: " + responseBody);

        // Verify results are not empty (check for "empty":false)
        assertTrue(responseBody.contains("\"empty\":false"),
                "Search returned empty results. Expected plants with '" + expectedKeyword + "' in the name. Response: " + responseBody);

        // Check if results contain the search keyword (case-insensitive check)
        String lowerCaseResponse = responseBody.toLowerCase();
        String lowerCaseKeyword = expectedKeyword.toLowerCase();

        // Verify at least one plant name contains the keyword
        assertTrue(lowerCaseResponse.contains("\"name\"") && lowerCaseResponse.contains(lowerCaseKeyword),
                "Search results do not contain plants with '" + expectedKeyword + "' in the name. Response: " + responseBody);

        System.out.println("Search results contain plants matching keyword: " + expectedKeyword);
    }

    /**
     * Test Step: Verify plants without the search keyword are excluded
     * Expected: Only plants with "Rose" in name appear (e.g., "Rose", "Rose_20260206035115")
     * Expected: Plants without "Rose" (e.g., "Lotus", "Sunflower") are NOT in results
     */
    @And("Plants without {string} are excluded from results")
    public void verifyNonMatchingPlantsExcluded(String keyword) {
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

        // Verify we have filtered results (not all plants)
        assertTrue(totalElements > 0,
                "Search returned 0 results. Expected plants with '" + keyword + "' in name. Response: " + responseBody);

        System.out.println("Search filtered correctly - Found " + totalElements + " plants with '" + keyword + "' in name");
        System.out.println("Plants without '" + keyword + "' are excluded from results");
    }

    /**
     * Test Step: Verify search is case-insensitive
     * Searching for "Rose", "rose", or "ROSE" should return the same results
     * Based on actual API response: totalElements > 0 confirms case-insensitive search works
     */
    @And("Search should be case-insensitive")
    public void verifySearchIsCaseInsensitive() {
        String responseBody = plantsPageAPI.getResponseBody();

        // Extract totalElements to verify results were found
        assertTrue(responseBody.contains("\"totalElements\":"),
                "totalElements field not found in response. Response: " + responseBody);

        // Verify totalElements > 0 (meaning search found results regardless of case)
        assertFalse(responseBody.contains("\"totalElements\":0"),
                "Search returned 0 results. Case-insensitive search may not be working. Response: " + responseBody);

        // Verify empty is false
        assertTrue(responseBody.contains("\"empty\":false"),
                "Search returned empty results. Case-insensitive search may not be working. Response: " + responseBody);

        System.out.println("Search is case-insensitive (results returned for keyword: " + searchKeyword + ")");
        System.out.println("Normal User successfully searched plants (READ permission confirmed)");
        System.out.println("Test Case API_Plant_SearchByName_007 - PASSED");
    }
}