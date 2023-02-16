package com.openmeet.webservice.exceptions;

/**
 *  Exception for invalid paramaters sended from requests
 */
public class InvalidParameterException extends RuntimeException {
    /**
     * @param s describe exception
     */
    public InvalidParameterException(String s) {
        super(s);
    }
}
