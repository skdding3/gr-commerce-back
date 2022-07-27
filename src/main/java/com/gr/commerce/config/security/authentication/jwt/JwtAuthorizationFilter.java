package com.gr.commerce.config.security.authentication.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.commerce.app.model.UserDetailsDto;
import com.gr.commerce.app.model.UserDto;
import com.gr.commerce.config.constant.AuthConstants;
import com.gr.commerce.utility.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return (
                new AntPathMatcher().match("/user/login", request.getServletPath())
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String accessToken = extractTokenFromRequestHeader(request, AuthConstants.AUTH_HEADER);
            String refreshToken = extractTokenFromRequestHeader(request, AuthConstants.RE_AUTH_HEADER);

            // Access-Token Validation
            if (JwtUtil.isValidToken(accessToken)) {

                checkAuthorization(accessToken);
                filterChain.doFilter(request, response);
                // When Initial Access-Token Expired
            } else if (JwtUtil.isValidRefreshToken(refreshToken)) {

                String newAccessToken = JwtUtil.generateJwt(newAccessTokenMadeByRefreshToken(refreshToken));
                settingResponseHeader(response, newAccessToken, refreshToken);
                filterChain.doFilter(request, response);

            } else {
                loginAgain(response);
            }
        } catch (NullPointerException e) {
            checkRequestHeader(response);
        }
    }

    // Extract Token from Header
    private static String extractTokenFromRequestHeader(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        return JwtUtil.getTokenFromBearer(bearerToken);
    }

    // Access-Token 재발급
    private static UserDto newAccessTokenMadeByRefreshToken(String refreshToken) {
        return UserDto.builder()
                .usrSeq(JwtUtil.getSeqFromToken(refreshToken))
                .usrEml(JwtUtil.getEmailFromToken(refreshToken))
                .usrId(JwtUtil.getIdFromToken(refreshToken))
                .usrTpCd(JwtUtil.getTypeFromToken(refreshToken))
                .build();
    }

    // Set New AccessToken in response Header
    private static void settingResponseHeader(HttpServletResponse response, String newAccessToken, String reToken) {
        String token = AuthConstants.TOKEN_TYPE + " " + newAccessToken;
        response.setHeader(AuthConstants.AUTH_HEADER, token);
        response.setHeader(AuthConstants.RE_AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + reToken);
    }

    // Authorization Check
    public void checkAuthorization(String token) {

        String typeCode = JwtUtil.getTypeFromToken(token);
        switch (typeCode) {
            case "0":
                typeCode = "ROLE_USER";
                break;
            case "1":
                typeCode = "ROLE_ADMIN";
                break;
        }

        UserDetailsDto loginUserDetails = new UserDetailsDto(UserDto.builder().usrTpCd(typeCode).build());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(null, null, loginUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Login Again Msg
    private static void loginAgain(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map err = new LinkedHashMap<>();
        err.put("resultCd", "401");
        err.put("resultMsg", "로그인을 다시 해주세요");
        err.put("data", -1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), err);
    }

    // Check Header Msg
    private static void checkRequestHeader(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map err = new LinkedHashMap<>();
        err.put("resultCd", "401");
        err.put("resultMsg", "토큰을 확인해주세요!!!");
        err.put("data", -1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), err);
    }

}
