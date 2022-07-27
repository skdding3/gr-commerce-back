package com.gr.commerce.config.security.authentication;

import com.gr.commerce.app.mapper.UserMapper;
import com.gr.commerce.app.model.UserDetailsDto;
import com.gr.commerce.app.service.CustomUserDetailsService;

import com.gr.commerce.config.constant.MsgConstants;
import com.gr.commerce.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String userEmail = token.getName();
        String userPassword = (String) token.getCredentials();

        UserDetailsDto loginUserDetails = (UserDetailsDto) userDetailsService.loadUserByUserEmail(userEmail);

        try {
            if (userMapper.findUserByEmail(userEmail) == null) {
                throw new UserNotFoundException(MsgConstants.USER_NOT_FOUND);
            }
            if (!passwordEncoder.matches(userPassword, loginUserDetails.getPassword())) {
                throw new BadCredentialsException(MsgConstants.INVALID_PASSWORD);
            }
        } catch (BadCredentialsException | UserNotFoundException e) {
            throw e;
        }

        return new UsernamePasswordAuthenticationToken(loginUserDetails, userPassword, loginUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
