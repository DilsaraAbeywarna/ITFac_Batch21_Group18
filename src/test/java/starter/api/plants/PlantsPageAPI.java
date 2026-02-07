package starter.api.plants;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import utils.TokenHolder;

import java.util.Map;

public class PlantsPageAPI {

    private Response response;

    // ==================== TEST CASES: 001-004, 009 - CREATE PLANT OPERATIONS ====================
    
    public void createPlantUnderCategory(int categoryId, Map<String, Object> plantBody) {
        response = SerenityRest.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + TokenHolder.getToken())
                .body(plantBody)
                .post("/api/plants/category/" + categoryId);
    }

    public void createPlantWithoutAuth(int categoryId, Map<String, Object> plantBody) {
        response = SerenityRest.given()
            .contentType("application/json")
            .body(plantBody)
            .post("/api/plants/category/" + categoryId);
    }

    // ==================== TEST CASES: 005, 006 - GET PLANT OPERATIONS ====================
    
    public void getPaginatedPlants(int page, int size) {
        response = SerenityRest.given()
                .header("Authorization", "Bearer " + TokenHolder.getToken())
                .queryParam("page", page)
                .queryParam("size", size)
                .get("/api/plants/paged");
    }

    public void getAllPlants() {
        response = SerenityRest.given()
                .header("Authorization", "Bearer " + TokenHolder.getToken())
                .get("/api/plants");
    }

    // ==================== TEST CASE: 007 - SEARCH PLANTS OPERATION ====================
    
    public void searchPlantsByName(String searchName, int page, int size) {
        response = SerenityRest.given()
                .header("Authorization", "Bearer " + TokenHolder.getToken())
                .queryParam("name", searchName)
                .queryParam("page", page)
                .queryParam("size", size)
                .get("/api/plants/paged");
    }

    // ==================== TEST CASE: 008 - FILTER PLANTS OPERATION ====================
    
    public void filterPlantsByCategory(int categoryId, int page, int size) {
        response = SerenityRest.given()
                .header("Authorization", "Bearer " + TokenHolder.getToken())
                .queryParam("categoryId", categoryId)
                .queryParam("page", page)
                .queryParam("size", size)
                .get("/api/plants/paged");
    }

    // ==================== RESPONSE HELPER METHODS ====================
    
    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getResponseBody() {
        return response.getBody().asString();
    }

    public Response getResponse() {
        return response;
    }
}