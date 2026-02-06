package utils;

public class TokenHolder {

    private static String currentToken;

    public static void setToken(String token) {
        currentToken = token;
    }

    public static String getToken() {
        return currentToken;
    }

    public static void clearToken() {
        currentToken = null;
    }
}