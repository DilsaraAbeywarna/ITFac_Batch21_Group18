package starter.api.plants;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import utils.TokenHolder;

import java.util.Map;

/**
 * API Page Object for Plants endpoints
 * Contains methods for interacting with the Plants API
 */
public class PlantsPageAPI {

    private Response response;

    /**
     * Create a plant under a specific category (sub-category)
     * Endpoint: POST /api/plants/category/{categoryId}
     * 
     * @param categoryId The category/sub-category ID
     * @param plantBody Plant details (name, price, quantity)
     */
    public void createPlantUnderCategory(int categoryId, Map<String, Object> plantBody) {
        response = SerenityRest.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + TokenHolder.getToken())
                .body(plantBody)
                .post("/api/plants/category/" + categoryId);
    }

    /**
     * Get the HTTP status code from the response
     * 
     * @return Status code (e.g., 201, 400, 403, 404, 500)
     */
    public int getStatusCode() {
        return response.getStatusCode();
    }

    /**
     * Get response body as string
     * 
     * @return Response body in string format
     */
    public String getResponseBody() {
        return response.getBody().asString();
    }

    /**
     * Get the full Response object for advanced assertions
     * 
     * @return Response object from REST Assured
     */
    public Response getResponse() {
        return response;
    }
}