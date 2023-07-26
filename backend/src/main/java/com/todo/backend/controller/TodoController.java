package com.todo.backend.controller;

import com.todo.backend.entity.Todo;
import com.todo.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Todo> saveTodo(@RequestBody Todo todo,
                                         @RequestHeader("Authorization") String accessToken) {
        Todo todoSaved = todoService.saveTodo(todo, accessToken);
        return new ResponseEntity<>(todoSaved, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Todo> getTodoById(@RequestParam("todoId") int todoId) {
        Todo todoSaved = todoService.getTodoById(todoId);
        return new ResponseEntity<>(todoSaved, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Todo>> getAllTodos(@RequestHeader("Authorization") String accessToken) {
        List<Todo> allTodos = todoService.getAllTodos(accessToken);
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Todo> updateTodoById(@RequestBody Map<String, Object> fields,
                               @RequestParam("todoId") int todoId) {
        Todo todo = todoService.updateTodoById(fields, todoId);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> deleteTodoById(@RequestParam("todoId") int todoId) {
        String message = todoService.deleteTodoById(todoId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
