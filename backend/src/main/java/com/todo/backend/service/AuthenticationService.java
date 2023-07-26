package com.todo.backend.service;

import com.todo.backend.entity.UserInfo;
import com.todo.backend.model.JwtTokenResponse;
import com.todo.backend.model.LoginRequest;
import com.todo.backend.model.RefreshTokenRequest;

public interface AuthenticationService {
    UserInfo signup(UserInfo userInfo);

    JwtTokenResponse login(LoginRequest loginRequest);

    JwtTokenResponse getAccessTokenByRefreshToken(String accessToken, RefreshTokenRequest refreshTokenRequest);
}
