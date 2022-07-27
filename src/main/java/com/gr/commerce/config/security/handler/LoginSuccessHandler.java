package com.gr.commerce.config.security.handler;

import com.gr.commerce.config.constant.AuthConstants;
import com.gr.commerce.config.constant.MsgConstants;
import com.gr.commerce.utility.JwtUtil;
import com.gr.commerce.app.model.UserDto;
import com.gr.commerce.app.model.UserDetailsDto;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println(" ==== ");
        // 인증 성공시 JWT 방행
        final UserDto loginUser = ((UserDetailsDto) authentication.getPrincipal()).getUserDto();
        final String accessToken = JwtUtil.generateJwt(loginUser);

        // 응답 헤더에 AccessToken, RefreshToken 보내기.
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);
        response.addHeader(AuthConstants.RE_AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + JwtUtil.reGenerateJwt(loginUser));
        response.addHeader(AuthConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().println(MsgConstants.loginSuccessMessage());
    }
}
