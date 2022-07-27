package com.gr.commerce.app.service.impl;

import com.gr.commerce.app.mapper.UserMapper;
import com.gr.commerce.app.model.UserDetailsDto;
import com.gr.commerce.app.model.UserDto;

import com.gr.commerce.app.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUserEmail(String email) throws UsernameNotFoundException {

        UserDto existUser = Optional.ofNullable(userMapper.findUserByEmail(email))
                .orElseGet(() -> noExistUser(email));
        return new UserDetailsDto(existUser);
    }

    private UserDto noExistUser(String email) {
        return UserDto.builder().usrEml(email).build();
    }
}