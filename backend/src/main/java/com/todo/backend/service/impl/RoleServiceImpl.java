package com.todo.backend.service.impl;

import com.todo.backend.entity.Role;
import com.todo.backend.repository.RoleRepository;
import com.todo.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveRolesInDataBase(List<Role> roleList) {
        roleRepository.saveAll(roleList);
    }
}
