package starter.api.category;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import utils.TokenHolder;

import java.util.Map;

/**
 * Page Object for Categories API - Edit/Update Category functionality
 * Handles all API interactions related to category updates and retrieval
 * Follows Page Object Model pattern for API automation
 */
public class EditCategoryPageAPI {

    // Constants - Centralized endpoint and header definitions
    private static final String BASE_CATEGORIES_ENDPOINT = "/api/categories";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    // Error messages
    private static final String NO_RESPONSE_ERROR = "No API response available. Ensure an API method has been called first.";

    // Response holder
    private Response response;

    /**
     * Retrieves all categories from the system
     * Used to dynamically fetch a category ID for testing
     * 
     * @return Response containing list of all categories
     */
    public Response getAllCategories() {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE_JSON)
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + TokenHolder.getToken())
                .log().all()
                .when()
                .get(BASE_CATEGORIES_ENDPOINT);

        response.then().log().all();
        return response;
    }

    /**
     * Updates an existing category via PUT request
     * 
     * @param categoryId   The ID of the category to update
     * @param categoryBody Map containing updated category details (e.g., "name")
     */
    public void updateCategory(int categoryId, Map<String, Object> categoryBody) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE_JSON)
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + TokenHolder.getToken())
                .body(categoryBody)
                .log().all()
                .when()
                .put(BASE_CATEGORIES_ENDPOINT + "/" + categoryId);

        response.then().log().all();
    }

    /**
     * Retrieves the HTTP status code from the last API response
     * 
     * @return HTTP status code (e.g., 200, 400, 403, 404, 500)
     * @throws IllegalStateException if no response is available
     */
    public int getStatusCode() {
        validateResponseExists();
        return response.getStatusCode();
    }

    /**
     * Retrieves the response body as a string
     * 
     * @return Response body in string format
     * @throws IllegalStateException if no response is available
     */
    public String getResponseBody() {
        validateResponseExists();
        return response.getBody().asString();
    }

    /**
     * Retrieves the full Response object for advanced assertions
     * 
     * @return RestAssured Response object
     * @throws IllegalStateException if no response is available
     */
    public Response getResponse() {
        validateResponseExists();
        return response;
    }

    /**
     * Extracts a specific field value from the JSON response using JSONPath
     * 
     * @param jsonPath JSONPath expression (e.g., "id", "name")
     * @param <T>      Expected return type
     * @return Value at the specified JSON path
     * @throws IllegalStateException if no response is available
     */
    public <T> T getJsonPathValue(String jsonPath) {
        validateResponseExists();
        return response.jsonPath().get(jsonPath);
    }

    /**
     * Validates that the response object exists before attempting to access it
     * 
     * @throws IllegalStateException if response is null
     */
    private void validateResponseExists() {
        if (response == null) {
            throw new IllegalStateException(NO_RESPONSE_ERROR);
        }
    }
}