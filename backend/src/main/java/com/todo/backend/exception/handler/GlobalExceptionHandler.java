package com.todo.backend.exception.handler;

import com.todo.backend.exception.CredentialsNotValidException;
import com.todo.backend.exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CredentialsNotValidException.class)
    public ResponseEntity<Map<String, String>> handleCredentialsNotValidException(CredentialsNotValidException e) {
        Map<String, String> returnMap = new HashMap<>();

        returnMap.put("message", e.getMessage());
        returnMap.put("status", "failed");

        return new ResponseEntity<>(returnMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTodoNotFoundException(TodoNotFoundException e) {
        Map<String, String> returnMap = new HashMap<>();

        returnMap.put("message", e.getMessage());
        returnMap.put("status", "failed");

        return new ResponseEntity<>(returnMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        Map<String, String> returnMap = new HashMap<>();

        returnMap.put("message", "Email already exists");
        returnMap.put("status", "failed");

        return new ResponseEntity<>(returnMap, HttpStatus.BAD_REQUEST);
    }
}
