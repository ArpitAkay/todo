package com.todo.backend.controller;

import com.todo.backend.entity.Role;
import com.todo.backend.service.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostConstruct
    public void saveRolesInDataBase() {
        List<Role> roleList = List.of(new Role(101, "ROLE_USER"), new Role(102, "ROLE_ADMIN"));
        roleService.saveRolesInDataBase(roleList);
    }
}
