package com.todo.backend.service.impl;

import com.todo.backend.entity.Todo;
import com.todo.backend.entity.UserInfo;
import com.todo.backend.exception.InvalidJwtToken;
import com.todo.backend.exception.TodoNotFoundException;
import com.todo.backend.repository.TodoRepository;
import com.todo.backend.repository.UserInfoRepository;
import com.todo.backend.service.TodoService;
import com.todo.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public Todo saveTodo(Todo todo, String accessToken) {
        accessToken = accessToken.substring(7);
        String email = jwtUtil.extractUsername(accessToken);

        UserInfo userInfo = userInfoRepository.findByEmail(email).orElseThrow(() -> new InvalidJwtToken("Access Token is invalid"));

        todo.setCompleted(false);
        todo.setCreatedAt(new Date());
        todo.setUserInfo(userInfo);

        return todoRepository.save(todo);
    }

    @Override
    public Todo getTodoById(int todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + todoId));
    }

    @Override
    public List<Todo> getAllTodos(String accessToken) {
        accessToken = accessToken.substring(7);
        String email = jwtUtil.extractUsername(accessToken);

        return todoRepository.findByEmail(email);
    }

    @Override
    public Todo updateTodoById(Map<String, Object> fields, int todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + todoId));
        todo.setUpdatedAt(new Date());
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Todo.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, todo, value);
        });

        return todoRepository.save(todo);
    }

    @Override
    public String deleteTodoById(int todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + todoId));
        todo.setUserInfo(null);
        todoRepository.save(todo);
        todoRepository.delete(todo);
        return "Deleted! Your todo is deleted success";
    }
}
