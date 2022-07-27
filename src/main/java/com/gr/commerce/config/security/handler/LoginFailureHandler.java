package com.gr.commerce.config.security.handler;

import com.gr.commerce.config.constant.MsgConstants;
import com.gr.commerce.config.constant.AuthConstants;
import com.gr.commerce.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        response.addHeader(AuthConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        if (exception instanceof UserNotFoundException) {
            response.getWriter().print(MsgConstants.userNotFound());

        } else if (exception instanceof BadCredentialsException) {
            response.getWriter().print(MsgConstants.invalidPassword());

        } else {
            response.getWriter().print(MsgConstants.loginFailedMessage());
        }
    }
}
