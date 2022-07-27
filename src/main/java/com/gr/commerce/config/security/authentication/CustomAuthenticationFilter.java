package com.gr.commerce.config.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.gr.commerce.app.model.UserDto;
import com.gr.commerce.exception.InputNotFoundException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UsernamePasswordAuthenticationToken authRequest;

    public CustomAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserDto loginUser = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
            authRequest = new UsernamePasswordAuthenticationToken(loginUser.getUsrEml(), loginUser.getUsrPw());
        } catch (IOException e) {
            throw new InputNotFoundException();
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
