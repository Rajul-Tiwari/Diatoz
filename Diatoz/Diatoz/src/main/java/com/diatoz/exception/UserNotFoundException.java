package com.diatoz.exception;

public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new ProjectException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

}
