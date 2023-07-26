package com.todo.backend.exception;

public class InvalidJwtToken extends RuntimeException {

    public InvalidJwtToken(String message) {
        super(message);
    }
}
