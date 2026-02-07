package utils;

public class TokenHolder {
    private static String token;

    public static void setToken(String authToken) {
        token = authToken;
    }

    public static String getToken() {
        return token;
    }

    public static void clearToken() {
        token = null;
    }
}