package com.todo.backend.service.impl;

import com.todo.backend.dto.UserInfoUserDetailsService;
import com.todo.backend.entity.Role;
import com.todo.backend.entity.UserInfo;
import com.todo.backend.exception.CredentialsNotValidException;
import com.todo.backend.model.JwtTokenResponse;
import com.todo.backend.model.LoginRequest;
import com.todo.backend.model.RefreshTokenRequest;
import com.todo.backend.repository.RoleRepository;
import com.todo.backend.repository.UserInfoRepository;
import com.todo.backend.service.AuthenticationService;
import com.todo.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final List<String> ADMIN_USERS = List.of("arpitkumar4000@gmail.com");

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserInfoUserDetailsService userInfoUserDetailsService;

    @Override
    public UserInfo signup(UserInfo userInfo) {
        String password = userInfo.getPassword();
        userInfo.setPassword(passwordEncoder.encode(password));

        Role role;
        if(ADMIN_USERS.contains(userInfo.getEmail())){
            role = roleRepository.findById(102).get();
        }
        else {
            role = roleRepository.findById(101).get();
        }
        userInfo.getRoles().add(role);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public JwtTokenResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        }
        catch(BadCredentialsException e) {
            throw new CredentialsNotValidException("Please enter correct password");
        }

        JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
        if(authentication.isAuthenticated()) {
            UserInfo userInfo = userInfoRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));

            String accessToken = jwtUtil.generateToken(userInfo);
            jwtTokenResponse.setAccessToken(accessToken);
            jwtTokenResponse.setAccessTokenIssued(new Date());
            jwtTokenResponse.setAccessTokenExpiration(jwtUtil.extractExpiration(accessToken));

            String refreshToken = jwtUtil.generateRefreshToken(userInfo);
            jwtTokenResponse.setRefreshToken(refreshToken);
            jwtTokenResponse.setRefreshTokenIssued(new Date());
            jwtTokenResponse.setRefreshTokenExpiration(jwtUtil.extractExpiration(refreshToken));
            return jwtTokenResponse;
        }
        else{
            throw new CredentialsNotValidException("Please enter correct credentials");
        }
    }

    @Override
    public JwtTokenResponse getAccessTokenByRefreshToken(String accessToken, RefreshTokenRequest refreshTokenRequest) {
        return null;
    }
}
