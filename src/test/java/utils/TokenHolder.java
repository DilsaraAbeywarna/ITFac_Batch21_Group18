package utils;

/**
 * Utility class to store and retrieve authentication tokens
 * Used for sharing JWT tokens across step definitions
 */
public class TokenHolder {
    private static String token;

    /**
     * Store a new authentication token
     * 
     * @param newToken JWT token to store
     */
    public static void setToken(String newToken) {
        token = newToken;
    }

    /**
     * Retrieve the stored authentication token
     * 
     * @return Current JWT token
     */
    public static String getToken() {
        return token;
    }

    /**
     * Clear the stored token
     * Useful for cleanup between test scenarios
     */
    public static void clearToken() {
        token = null;
    }
}