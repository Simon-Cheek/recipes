package com.simon.recipes.exceptions;

public class MissingInfoException extends RuntimeException {
    public MissingInfoException(String message) {
        super(message);
    }
}
