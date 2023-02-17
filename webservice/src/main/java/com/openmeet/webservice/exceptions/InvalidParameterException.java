package com.openmeet.webservice.exceptions;

/**
 *  Exception for invalid parameters sent from requests
 */
public class InvalidParameterException extends RuntimeException {
    /**
     * @param s describes exception
     */
    public InvalidParameterException(String s) {
        super(s);
    }
}
