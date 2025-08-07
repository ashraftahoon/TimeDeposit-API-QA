package utils;

public class TestContext {
    private static String authToken;
    private static boolean isAdmin;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String token) {
        authToken = token;
    }

    public static void setAuthToken(String token, boolean admin) {
        authToken = token;
        isAdmin = admin;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }
}