package com.todo.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int todoId;
    private String content;
    private boolean completed;
    private Date createdAt;
    private Date updatedAt;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "email",
            referencedColumnName = "email"
    )
    private UserInfo userInfo;
}
