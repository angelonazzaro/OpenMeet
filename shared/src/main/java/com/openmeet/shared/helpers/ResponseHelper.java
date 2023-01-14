package com.openmeet.shared.helpers;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * This class contains functions that help to send responses to the client.
 *
 * @author Angelo Nazzaro
 */
public class ResponseHelper {

    private static final JSONResponse jsonResponse = new JSONResponse();
    private static final Gson gson = new Gson();

    /**
     * Checks if all the parameters are initialized.
     *
     * @param parameters The parameters to control.
     * @return The result of the check.
     *
     * @author Angelo Nazzaro
     **/
    public static boolean checkStringFields(String[] parameters) {

        for (String parameter : parameters) {
            if (parameter == null || parameter.length() == 0) return false;
        }

        return true;
    }

    /**
     * Sends a generic error message to the client.
     *
     * @param out The Writer with which the response will be sent.
     *
     * @author Angelo Nazzaro
     * */
    public static void sendGenericError(PrintWriter out) {

        jsonResponse.addPair("status", "error");
        jsonResponse.addPair("message", "An error occurred, please try again later.");

        writeResponse(out);
    }

    /**
     * Sends a custom error message to the client.
     *
     * @param out The Writer with which the response will be sent.
     *
     * @author Angelo Nazzaro
     * */
    public static void sendCustomError(PrintWriter out, String value) {

        jsonResponse.addPair("status", "error");
        jsonResponse.addPair("message", value);

        writeResponse(out);
    }

    /**
     * Sends a generic success message to the client.
     *
     * @param out The Writer with which the response will be sent.
     *
     * @author Angelo Nazzaro
     * */
    public static void sendCustomSuccess(PrintWriter out, String value) {

        jsonResponse.addPair("status", "success");
        jsonResponse.addPair("message", value);

        writeResponse(out);
    }

    /**
     * Sends a generic response message to the client.
     *
     * @param out The Writer with which the response will be sent.
     *
     * @author Angelo Nazzaro
     * */
    public static void sendGenericResponse(PrintWriter out, HashMap<String, String> pairs) {

        pairs.entrySet().forEach(entry -> {
            jsonResponse.addPair(entry.getKey(), entry.getValue());
        });

        writeResponse(out);
    }

    /**
     * Sends the response to the client and clear the jsonResponse.
     *
     * @param out The Writer with which the response will be sent.
     *
     * @author Angelo Nazzaro
     * */
    private static void writeResponse(PrintWriter out) {
        out.write(gson.toJson(jsonResponse.getResponse()));
        out.flush();
        jsonResponse.clear();
    }

}
