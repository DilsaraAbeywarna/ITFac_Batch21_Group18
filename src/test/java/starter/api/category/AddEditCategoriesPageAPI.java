package starter.api.category;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import utils.TokenHolder;

import java.util.Map;

public class AddEditCategoriesPageAPI {

    // constants for API endpoints, headers, and error messages
    private static final String BASE_CATEGORIES_ENDPOINT = "/api/categories";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String NO_RESPONSE_ERROR = "No API response available. Ensure an API method has been called first.";

    // create operations

    /**
     * Creates a category using POST request
     * This method handles BOTH successful creation (201) and validation errors
     * (400)
     * 
     * IMPORTANT: With SerenityRest, we don't need to store response in a variable.
     * SerenityRest.lastResponse() will give us the last response automatically.
     */
    public void createCategory(Map<String, Object> categoryBody) {
        SerenityRest.given()
                .contentType(CONTENT_TYPE_JSON)
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + TokenHolder.getToken())
                .body(categoryBody)
                .log().all()
                .when()
                .post(BASE_CATEGORIES_ENDPOINT)
                .then()
                .log().all();
    }

    // get all categories
    public Response getAllCategories() {
        SerenityRest.given()
                .contentType(CONTENT_TYPE_JSON)
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + TokenHolder.getToken())
                .log().all()
                .when()
                .get(BASE_CATEGORIES_ENDPOINT)
                .then()
                .log().all();

        return SerenityRest.lastResponse();
    }

    // get category by ID
    public Response getCategoryById(int categoryId) {
        SerenityRest.given()
                .contentType(CONTENT_TYPE_JSON)
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + TokenHolder.getToken())
                .log().all()
                .when()
                .get(BASE_CATEGORIES_ENDPOINT + "/" + categoryId)
                .then()
                .log().all();

        return SerenityRest.lastResponse();
    }

    // update operations

    /**
     * Updates a category using PUT request
     * This method handles BOTH successful updates (200) and validation errors (400)
     */
    public void updateCategory(int categoryId, Map<String, Object> categoryBody) {
        SerenityRest.given()
                .contentType(CONTENT_TYPE_JSON)
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + TokenHolder.getToken())
                .body(categoryBody)
                .log().all()
                .when()
                .put(BASE_CATEGORIES_ENDPOINT + "/" + categoryId)
                .then()
                .log().all();
    }

    /**
     * Deletes a category using DELETE request
     * This method handles BOTH successful deletion (204) and unauthorized errors
     * (403)
     */
    public void deleteCategory(int categoryId) {
        SerenityRest.given()
                .contentType(CONTENT_TYPE_JSON)
                .header(HEADER_AUTHORIZATION, BEARER_PREFIX + TokenHolder.getToken())
                .log().all()
                .when()
                .delete(BASE_CATEGORIES_ENDPOINT + "/" + categoryId)
                .then()
                .log().all();
    }

    // response handling methods

    /**
     * Gets the status code from the last response
     * SerenityRest automatically stores the last response
     */
    public int getStatusCode() {
        validateResponseExists();
        return SerenityRest.lastResponse().getStatusCode();
    }

    /**
     * Gets the response body as string from the last response
     */
    public String getResponseBody() {
        validateResponseExists();
        return SerenityRest.lastResponse().getBody().asString();
    }

    /**
     * Gets the last Response object
     * SerenityRest.lastResponse() returns the most recent response
     */
    public Response getResponse() {
        validateResponseExists();
        return SerenityRest.lastResponse();
    }

    /**
     * Extracts a specific field value from the JSON response using JSONPath
     */
    public <T> T getJsonPathValue(String jsonPath) {
        validateResponseExists();
        return SerenityRest.lastResponse().jsonPath().get(jsonPath);
    }

    // utility methods

    /**
     * Validates that a response exists
     * With SerenityRest, we check if lastResponse() is not null
     */
    private void validateResponseExists() {
        if (SerenityRest.lastResponse() == null) {
            throw new IllegalStateException(NO_RESPONSE_ERROR);
        }
    }

    /**
     * Resets the SerenityRest state
     */
    public void resetResponse() {
        SerenityRest.reset();
    }

    /**
     * Checks if a response is available
     */
    public boolean hasResponse() {
        return SerenityRest.lastResponse() != null;
    }
}