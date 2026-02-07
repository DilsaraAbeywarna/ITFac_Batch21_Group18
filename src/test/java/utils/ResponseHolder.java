package utils;

import io.restassured.response.Response;

public class ResponseHolder {

    private static Response currentResponse;

    public static void setResponse(Response response) {
        currentResponse = response;
    }

    public static Response getResponse() {
        return currentResponse;
    }

    public static void clearResponse() {
        currentResponse = null;
    }
}
