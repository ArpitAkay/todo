package com.todo.backend.repository;

import com.todo.backend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Query(
            value = "SELECT * FROM todo WHERE email = ?1",
            nativeQuery = true
    )
    List<Todo> findByEmail(String email);
}
