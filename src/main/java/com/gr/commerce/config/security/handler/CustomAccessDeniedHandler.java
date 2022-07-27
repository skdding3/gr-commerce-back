package com.gr.commerce.config.security.handler;

import com.gr.commerce.config.constant.AuthConstants;
import com.gr.commerce.config.constant.MsgConstants;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.CONTENT_TYPE);
        response.getWriter().print(MsgConstants.authenticationFailed());
    }
}
