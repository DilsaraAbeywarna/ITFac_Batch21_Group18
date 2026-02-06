package API;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;
import java.util.Map;

public class AuthApi {

    private static final String DEFAULT_LOGIN_PATH = "/api/auth/login";

    private Response response;
    private String token;

    public void login(String username, String password) {
        String path = System.getProperty("api.auth.login.path", DEFAULT_LOGIN_PATH);

        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(path)
                .then()
                .extract()
                .response();

        if (response.statusCode() >= 400) {
            throw new AssertionError("Login failed with status code: " + response.statusCode());
        }

        token = extractToken();
        if (token == null || token.trim().isEmpty()) {
            throw new AssertionError("Login response did not contain an access token.");
        }
    }

    public String getToken() {
        return token;
    }

    private String extractToken() {
        String tokenValue = response.jsonPath().getString("token");
        if (tokenValue == null || tokenValue.trim().isEmpty()) {
            tokenValue = response.jsonPath().getString("accessToken");
        }
        if (tokenValue == null || tokenValue.trim().isEmpty()) {
            tokenValue = response.jsonPath().getString("jwt");
        }
        if (tokenValue == null || tokenValue.trim().isEmpty()) {
            tokenValue = response.jsonPath().getString("data.token");
        }
        return tokenValue;
    }
}
