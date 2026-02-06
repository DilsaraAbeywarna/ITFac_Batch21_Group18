package starter.hooks;

import API.AuthApi;
import io.cucumber.java.Before;
import utils.TokenHolder;

public class AuthHooks {

    private static String adminToken;
    private static String nonAdminToken;

    // ===============================
    // ADMIN API LOGIN
    // ===============================
    @Before("@adminapi")
    public void loginAsAdminApi() {
        System.out.println("🔐 AuthHooks: Attempting admin login...");
        
        if (adminToken == null) {
            try {
                AuthApi authApi = new AuthApi();
                authApi.login("admin", "admin123");
                adminToken = authApi.getToken();
                
                System.out.println("✓ Login successful! Token: " + (adminToken != null ? adminToken.substring(0, Math.min(20, adminToken.length())) + "..." : "NULL"));
            } catch (Exception e) {
                System.err.println("❌ Login failed: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } else {
            System.out.println("✓ Using cached admin token");
        }
        
        // Store the token in TokenHolder for subsequent API calls
        TokenHolder.setToken(adminToken);
        System.out.println("✓ Token stored in TokenHolder");
    }

    // ===============================
    // NON ADMIN API LOGIN
    // ===============================
    @Before("@nonadminapi")
    public void loginAsNonAdminApi() {
        System.out.println("🔐 AuthHooks: Attempting non-admin login...");
        
        if (nonAdminToken == null) {
            try {
                AuthApi authApi = new AuthApi();
                authApi.login("testuser", "test123");
                nonAdminToken = authApi.getToken();
                
                System.out.println("✓ Login successful! Token: " + (nonAdminToken != null ? nonAdminToken.substring(0, Math.min(20, nonAdminToken.length())) + "..." : "NULL"));
            } catch (Exception e) {
                System.err.println("❌ Login failed: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } else {
            System.out.println("✓ Using cached non-admin token");
        }
        
        // Store the token in TokenHolder for subsequent API calls
        TokenHolder.setToken(nonAdminToken);
        System.out.println("✓ Token stored in TokenHolder");
    }
}
