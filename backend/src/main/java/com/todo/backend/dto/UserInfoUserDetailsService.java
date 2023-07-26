package com.todo.backend.dto;

import com.todo.backend.entity.UserInfo;
import com.todo.backend.exception.CredentialsNotValidException;
import com.todo.backend.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);

        if(userInfo.isPresent()) {
            return new UserInfoUserDetails(userInfo.get());
        }
        else {
            throw new CredentialsNotValidException("Please enter correct email");
        }
    }
}
