package com.openmeet.webapp;

import java.util.HashMap;

/**
 * Class for building a JSON Response
 * */
public class JSONResponse {
    private HashMap<String, String> response;

    public JSONResponse() {
        response = new HashMap<>();
    }

    public HashMap<String, String> getResponse() {
        return this.response;
    }

    public void addPair(String key, String value) {
        response.put(key, value);
    }

    public String getValue(String key) {
        if (!response.containsKey(key)) return null;

        return response.get(key);
    }

    public void clear() {
        response.clear();
    }

    /**
     * Returns the response in a JSON-like format
     * <p>
     * Let's assume that the response object has the following values:
     * <p>
     * {status=error, msg=something went wrong}
     * <p>
     * The returned string would look like:
     * <p>
     * {
     *     "status": error,
     *     "msg": "something went wrong",
     * }
     * */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n");
        response.forEach((key, value) -> {
            sb.append(String.format("\t%s: %s, \n", key, value));
        });

        sb.append("}");

        return sb.toString();
    }
}
