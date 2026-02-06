package utils;

import io.restassured.response.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseHolder {

    private static ThreadLocal<Map<String, Response>> responses = 
        ThreadLocal.withInitial(ConcurrentHashMap::new);

    public static void setResponse(String key, Response response) {
        responses.get().put(key, response);
    }

    public static Response getResponse(String key) {
        return responses.get().get(key);
    }
    
    // For backward compatibility
    public static void setResponse(Response response) {
        responses.get().put("default", response);
    }

    public static Response getResponse() {
        return responses.get().get("default");
    }

    public static void clearResponse() {
        responses.get().clear();
    }
    
    public static void clearResponse(String key) {
        responses.get().remove(key);
    }
}