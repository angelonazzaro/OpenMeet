package com.openmeet.shared.exceptions;

public class InvalidPrimaryKeyException extends RuntimeException {

    public InvalidPrimaryKeyException() {

        super("Invalid primary key exception");
    }

    public InvalidPrimaryKeyException(String message) {

        super(message);
    }

    public InvalidPrimaryKeyException(String message, String key) {

        super(message + ": " + key);
    }
}