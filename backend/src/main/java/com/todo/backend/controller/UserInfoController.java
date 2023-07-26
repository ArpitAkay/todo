package com.todo.backend.controller;

import com.todo.backend.entity.UserInfo;
import com.todo.backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<UserInfo> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        UserInfo userInfo = userInfoService.getUserInfo(accessToken);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
