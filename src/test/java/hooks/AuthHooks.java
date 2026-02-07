package hooks;

import api.AuthApi;
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
        if (adminToken == null) {
            AuthApi authApi = new AuthApi();
            authApi.login("admin", "admin123");
            adminToken = authApi.getToken();
        }
        // Store the token in TokenHolder for subsequent API calls
        TokenHolder.setToken(adminToken);
    }

    // ===============================
    // NON ADMIN API LOGIN
    // ===============================
    @Before("@nonadminapi")
    public void loginAsNonAdminApi() {
        if (nonAdminToken == null) {
            AuthApi authApi = new AuthApi();
            authApi.login("testuser", "test123");
            nonAdminToken = authApi.getToken();
        }
        // Store the token in TokenHolder for subsequent API calls
        TokenHolder.setToken(nonAdminToken);
    }
}
