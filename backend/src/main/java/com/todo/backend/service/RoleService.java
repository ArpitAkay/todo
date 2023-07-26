package com.todo.backend.service;

import com.todo.backend.entity.Role;

import java.util.List;

public interface RoleService {
    void saveRolesInDataBase(List<Role> roleList);
}
