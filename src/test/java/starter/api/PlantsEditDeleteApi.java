package starter.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

public class PlantsEditDeleteApi {
    private String baseUrl;
    private String bearerToken;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setBearerToken(String token) {
        this.bearerToken = token;
    }

    private RequestSpecification getRequestSpec() {
        RequestSpecification spec = SerenityRest.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .accept("*/*");
        
        if (bearerToken != null && !bearerToken.isEmpty()) {
            spec = spec.header("Authorization", "Bearer " + bearerToken);
        }
        
        return spec;
    }

    public Response updatePlant(String endpoint, String requestBody) {
        return getRequestSpec()
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public Response getPlant(Integer plantId) {
        return getRequestSpec()
                .when()
                .get("/api/plants/" + plantId);
    }

    public Response login(String username, String password) {
        String loginBody = String.format(
            "{\"username\":\"%s\",\"password\":\"%s\"}",
            username, password
        );
        
        return SerenityRest.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/api/auth/login");
    }

    public Response deletePlant(String endpoint) {
        return getRequestSpec()
                .when()
                .delete(endpoint);
    }

    public Response createPlant(Integer categoryId, String requestBody) {
        return getRequestSpec()
                .body(requestBody)
                .when()
                .post("/api/plants/category/" + categoryId);
    }
}
