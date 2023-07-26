package com.todo.backend.service;

import com.todo.backend.entity.Todo;

import java.util.List;
import java.util.Map;

public interface TodoService {
    Todo saveTodo(Todo todo, String accessToken);

    Todo getTodoById(int todoId);

    List<Todo> getAllTodos(String accessToken);

    Todo updateTodoById(Map<String, Object> fields, int todoId);

    String deleteTodoById(int todoId);
}
