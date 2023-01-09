package com.openmeet.webapp.helpers;

import com.google.gson.Gson;
import com.openmeet.webapp.JSONResponse;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * This class contains functions that help to send responses to the client.
 *
 * @author Angelo Nazzaro
 */
public class ResponseHelper {

  private static final JSONResponse jsonResponse = new JSONResponse();

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
      if (parameter == null || parameter.length() == 0) return true;
    }

    return false;
  }

  /**
   * Sends a generic error message to the client.
   *
   * @param out The Writer with which the response will be sent.
   * @param gson Gson Object instance to convert a Java Object to a JSON object.
   *
   * @author Angelo Nazzaro
   * */
  public static void sendGenericError(PrintWriter out, Gson gson) {

    jsonResponse.addPair("status", "error");
    jsonResponse.addPair("message", "An error occurred, please try again later.");

    writeResponse(out, gson);
  }

  /**
   * Sends a custom error message to the client.
   *
   * @param out The Writer with which the response will be sent.
   * @param gson Gson Object instance to convert a Java Object to a JSON object.
   *
   * @author Angelo Nazzaro
   * */
  public static void sendCustomError(PrintWriter out, Gson gson, String value) {

    jsonResponse.addPair("status", "error");
    jsonResponse.addPair("message", value);

    writeResponse(out, gson);
  }

  /**
   * Sends a generic success message to the client.
   *
   * @param out The Writer with which the response will be sent.
   * @param gson Gson Object instance to convert a Java Object to a JSON object.
   *
   * @author Angelo Nazzaro
   * */
  public static void sendCustomSuccess(PrintWriter out, Gson gson, String value) {

    jsonResponse.addPair("status", "success");
    jsonResponse.addPair("message", value);

    writeResponse(out, gson);
  }

  /**
   * Sends a generic response message to the client.
   *
   * @param out The Writer with which the response will be sent.
   * @param gson Gson Object instance to convert a Java Object to a JSON object.
   *
   * @author Angelo Nazzaro
   * */
  public static void sendGenericResponse(PrintWriter out, Gson gson, HashMap<String, String> pairs) {

    pairs.entrySet().forEach(entry -> {
      jsonResponse.addPair(entry.getKey(), entry.getValue());
    });

    writeResponse(out, gson);
  }

  /**
   * Sends the response to the client and clear the jsonResponse.
   *
   * @param out The Writer with which the response will be sent.
   * @param gson Gson Object instance to convert a Java Object to a JSON object.
   *
   * @author Angelo Nazzaro
   * */
  private static void writeResponse(PrintWriter out, Gson gson) {
    out.write(gson.toJson(jsonResponse.getResponse()));
    out.flush();
    jsonResponse.clear();
  }

}
