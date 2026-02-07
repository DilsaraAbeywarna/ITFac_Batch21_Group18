package starter.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import utils.TokenHolder;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.openqa.selenium.WebDriver;
import net.serenitybdd.core.Serenity;

public class Hooks {

    // ==================== API AUTHENTICATION HOOKS ====================

    @Before("@adminapi")
    public void setupAdminToken() {
        try {
            TokenHolder.clearToken();
            
            Response response = SerenityRest.given()
                    .contentType("application/json")
                    .body("{ \"username\": \"admin\", \"password\": \"admin123\" }")
                    .post("/api/auth/login");
            
            int statusCode = response.getStatusCode();
            
            if (statusCode == 200) {
                String token = response.jsonPath().getString("token");
                
                if (token != null && !token.trim().isEmpty()) {
                    TokenHolder.setToken(token);
                } else {
                    throw new RuntimeException("Admin token is null or empty");
                }
            } else {
                throw new RuntimeException("Admin login failed with status: " + statusCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Admin token setup failed", e);
        }
    }

    @Before("@nonadminapi")
    public void setupNonAdminToken() {
        try {
            TokenHolder.clearToken();
            
            Response response = SerenityRest.given()
                    .contentType("application/json")
                    .body("{ \"username\": \"testuser\", \"password\": \"test123\" }")
                    .post("/api/auth/login");
            
            int statusCode = response.getStatusCode();
            
            if (statusCode == 200) {
                String token = response.jsonPath().getString("token");
                
                if (token != null && !token.trim().isEmpty()) {
                    TokenHolder.setToken(token);
                } else {
                    throw new RuntimeException("User token is null or empty");
                }
            } else {
                throw new RuntimeException("User login failed with status: " + statusCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("User token setup failed", e);
        }
    }

    // ==================== CLEANUP HOOKS ====================

    @After("@ui")
    public void cleanupUISession() {
        try {
            WebDriver driver = Serenity.getDriver();
            if (driver != null) {
                driver.manage().deleteAllCookies();
            }
        } catch (Exception e) {
            // Silent cleanup - don't fail tests
        }
    }

    @After("@adminapi or @nonadminapi")
    public void cleanupAPISession() {
        try {
            if (TokenHolder.hasToken()) {
                TokenHolder.clearToken();
            }
        } catch (Exception e) {
            // Silent cleanup - don't fail tests
        }
    }

    @After(order = 0)
    public void globalCleanup() {
        // Global cleanup placeholder
    }
}