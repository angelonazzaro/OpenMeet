package com.openmeet.webapp.helpers;

import com.google.gson.Gson;
import com.openmeet.webapp.JSONResponse;

import java.io.PrintWriter;
import java.util.HashMap;

public class ResponseHelper {

    private static final JSONResponse jsonResponse = new JSONResponse();
    public static boolean checkStringFields(String[] parameters) {

        for (String parameter : parameters) {
            if (parameter == null || parameter.length() == 0) return true;
        }

        return false;
    }

    public static void sendGenericError(PrintWriter out, Gson gson) {

        jsonResponse.addPair("status", "error");
        jsonResponse.addPair("message", "An error occurred, please try again later.");

        writeResponse(out, gson);
    }

    public static void sendCustomError(PrintWriter out, Gson gson, String value) {

        jsonResponse.addPair("status", "error");
        jsonResponse.addPair("message", value);

        writeResponse(out, gson);
    }

    public static void sendCustomSuccess(PrintWriter out, Gson gson, String value) {

        jsonResponse.addPair("status", "success");
        jsonResponse.addPair("message", value);

        writeResponse(out, gson);
    }

    public static void sendGenericResponse(PrintWriter out, Gson gson, HashMap<String, String> pairs) {

        pairs.entrySet().forEach(entry -> {
            jsonResponse.addPair(entry.getKey(), entry.getValue());
        });

        writeResponse(out, gson);
    }

    private static void writeResponse(PrintWriter out, Gson gson) {
        out.write(gson.toJson(jsonResponse.getResponse()));
        out.flush();
        jsonResponse.clear();
    }

}
