package com.todo.backend.controller;

import com.todo.backend.entity.UserInfo;
import com.todo.backend.model.JwtTokenResponse;
import com.todo.backend.model.LoginRequest;
import com.todo.backend.model.RefreshTokenRequest;
import com.todo.backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfo> signup(@RequestBody UserInfo userInfo) {
        UserInfo userInfoSaved = authenticationService.signup(userInfo);
        return new ResponseEntity<>(userInfoSaved, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtTokenResponse jwtTokenResponse = authenticationService.login(loginRequest);
        return new ResponseEntity<>(jwtTokenResponse, HttpStatus.OK);
    }

    @PostMapping("/access-token")
    public ResponseEntity<JwtTokenResponse> getAccessTokenByRefreshToken(@RequestHeader("Authorization") String accessToken,
                                                                         @RequestBody RefreshTokenRequest refreshTokenRequest) {
        JwtTokenResponse jwtTokenResponse = authenticationService.getAccessTokenByRefreshToken(accessToken, refreshTokenRequest);
        return new ResponseEntity<>(jwtTokenResponse, HttpStatus.OK);
    }
}
