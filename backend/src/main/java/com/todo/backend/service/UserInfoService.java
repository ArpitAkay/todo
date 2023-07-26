package com.todo.backend.service;

import com.todo.backend.entity.UserInfo;

public interface UserInfoService {
    UserInfo getUserInfo(String accessToken);
}
