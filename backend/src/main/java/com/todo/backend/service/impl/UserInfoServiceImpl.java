package com.todo.backend.service.impl;

import com.todo.backend.entity.UserInfo;
import com.todo.backend.exception.InvalidJwtToken;
import com.todo.backend.repository.UserInfoRepository;
import com.todo.backend.service.UserInfoService;
import com.todo.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserInfo getUserInfo(String accessToken) {
        accessToken = accessToken.substring(7);
        String email = jwtUtil.extractUsername(accessToken);
        return userInfoRepository.findByEmail(email).orElseThrow(() ->  new InvalidJwtToken("Access Token is invalid"));
    }
}
