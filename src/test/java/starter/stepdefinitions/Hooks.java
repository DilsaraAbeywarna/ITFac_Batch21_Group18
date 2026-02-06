package starter.stepdefinitions;

import io.cucumber.java.Before;
import utils.TokenHolder;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

/**
 * Cucumber Hooks for setting up preconditions
 * Handles authentication token generation for different user roles
 */
public class Hooks {

    /**
     * Hook for @adminapi tagged scenarios
     * Generates and stores Admin authentication token before scenario execution
     */
    @Before("@adminapi")
    public void setupAdminToken() {
        System.out.println("🔐 Setting up Admin token for @adminapi scenario");

        // Generate Admin token via login endpoint
        Response response = SerenityRest.given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\", \"password\": \"admin123\" }")
                .post("/api/auth/login");

        // Extract token from response
        String token = response.jsonPath().getString("token");
        TokenHolder.setToken(token);

        System.out.println("Admin token generated successfully");
    }

    /**
     * Hook for @nonadminapi tagged scenarios
     * Generates and stores regular User authentication token before scenario
     * execution
     */
    @Before("@nonadminapi")
    public void setupNonAdminToken() {
        System.out.println("🔐 Setting up User token for @nonadminapi scenario");

        // Generate User token (testuser) via login endpoint
        Response response = SerenityRest.given()
                .contentType("application/json")
                .body("{ \"username\": \"testuser\", \"password\": \"test123\" }")
                .post("/api/auth/login");

        // Extract token from response
        String token = response.jsonPath().getString("token");
        TokenHolder.setToken(token);

        System.out.println("User token generated successfully");
    }
}