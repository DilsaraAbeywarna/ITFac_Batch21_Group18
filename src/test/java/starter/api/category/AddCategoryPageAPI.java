package starter.api.category;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import utils.TokenHolder;

import java.util.Map;

/**
 * Page Object for Categories API
 * Handles all API interactions related to category management
 * Follows Page Object Model pattern for API automation
 */
public class AddCategoryPageAPI {

    // Constants - Centralized endpoint and header definitions
    private static final String BASE_CATEGORIES_ENDPOINT = "/api/categories";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    // Error messages
    private static final String NO_RESPONSE_ERROR = "No API response available. Ensure an API method (e.g., createCategory()) has been called first.";

    // Response holder
    private Response response;

    /**
     * Creates a new category via POST request
     * Handles both valid and invalid requests for testing purposes
     * No client-side validation - allows API to perform validation
     * 
     * @param categoryBody Map containing category details (e.g., "name")
     */
    public void createCategory(Map<String, Object> categoryBody) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE_JSON)
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + TokenHolder.getToken())
                .body(categoryBody)
                .log().all()
                .when()
                .post(BASE_CATEGORIES_ENDPOINT);

        response.then().log().all();
    }

    /**
     * Retrieves the HTTP status code from the last API response
     * 
     * @return HTTP status code (e.g., 201, 400, 403, 404, 500)
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
     * Retrieves the full Response object for advanced assertions and JSON path
     * operations
     * Use this when you need to extract specific fields using JSONPath
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
     * @param jsonPath JSONPath expression (e.g., "id", "name", "details.name")
     * @param <T>      Expected return type
     * @return Value at the specified JSON path, or null if path doesn't exist
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

    /**
     * Resets the response state
     * Useful for cleanup between test scenarios
     */
    public void resetResponse() {
        this.response = null;
    }

    /**
     * Checks if a response is available
     * 
     * @return true if response exists, false otherwise
     */
    public boolean hasResponse() {
        return response != null;
    }
}