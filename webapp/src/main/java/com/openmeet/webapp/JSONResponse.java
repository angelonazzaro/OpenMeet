package com.openmeet.webapp;

import java.util.HashMap;

/**
 * Class for building a JSON Response.
 *
 * @author Angelo Nazzaro
 */
public class JSONResponse {
  private HashMap<String, String> response;

  public JSONResponse() {
    response = new HashMap<>();
  }

  /**
   * Returns the response.
   *
   * @return the response.
   *
   * @author Angelo Nazzaro
   * */
  public HashMap<String, String> getResponse() {
    return this.response;
  }

  /**
   * Adds a pair to the response.
   *
   * @param key The key of the pair.
   * @param value The value of the pair.
   *
   * @author Angelo Nazzaro
   * */
  public void addPair(String key, String value) {
    response.put(key, value);
  }


  /**
   * Returns the value of the key.
   *
   * @param key The JSON object key.
   * @return The value of the key.
   *
   * @author Angelo Nazzaro
   * */
  public String getValue(String key) {
    if (!response.containsKey(key)) return null;

    return response.get(key);
  }

  /**
   * Clears the response.
   *
   * @author Angelo Nazzaro
   * */
  public void clear() {
    response.clear();
  }

  /**
   * Returns the response as a JSON string.
   *
   * @return this object in a JSON-like format.
   *
   * @author Angelo Nazzaro
   */
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
