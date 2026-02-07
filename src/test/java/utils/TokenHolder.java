package utils;

public class TokenHolder {
    
    private static ThreadLocal<String> token = new ThreadLocal<>();

    public static void setToken(String newToken) {
        if (newToken == null || newToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        token.set(newToken);
    }

    public static String getToken() {
        return token.get();
    }

    public static void clearToken() {
        token.remove();
    }

    public static boolean hasToken() {
        String currentToken = token.get();
        return currentToken != null && !currentToken.trim().isEmpty();
    }

    public static String getTokenOrThrow() {
        String currentToken = token.get();
        if (currentToken == null || currentToken.trim().isEmpty()) {
            throw new IllegalStateException("Authentication token is not set! " +
                "Make sure @adminapi or @nonadminapi hook is executed before this step.");
        }
        return currentToken;
    }
}