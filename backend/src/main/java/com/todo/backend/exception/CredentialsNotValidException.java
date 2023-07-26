package com.todo.backend.exception;

public class CredentialsNotValidException extends RuntimeException{

    public CredentialsNotValidException(String message) {
        super(message);
    }
}
