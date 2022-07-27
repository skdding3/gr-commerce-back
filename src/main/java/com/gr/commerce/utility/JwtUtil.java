package com.gr.commerce.utility;

import com.gr.commerce.config.KeyConfiguration;
import io.jsonwebtoken.*;
import com.gr.commerce.app.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;

@Component
public class JwtUtil {

    static KeyConfiguration keyConfiguration;

    @Autowired
    public JwtUtil(KeyConfiguration keyConfiguration) {
        this.keyConfiguration = keyConfiguration;
    }

    /******************************************************************************
     * TOKEN GENERATE METHODS
     *****************************************************************************/
    // Access-Token 생성
    public static String generateJwt(UserDto userDto) {
        return Jwts.builder()
                .setHeader(createJwtHeader())
                .setSubject(userDto.getUsrEml())
                .setClaims(createClaims(userDto))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(createAccessTokenExpirationTime())
                .signWith(SignatureAlgorithm.HS256, createSigningKey())
                .compact();
    }

    // Refresh-Token 생성
    public static String reGenerateJwt(UserDto userDto) {
        return Jwts.builder()
                .setHeader(createJwtHeader())
                .setSubject(userDto.getUsrEml())
                .setClaims(createClaims(userDto))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(createRefreshTokenExpirationTime())
                .signWith(SignatureAlgorithm.HS256, createSigningKey())
                .compact();
    }

    // Jwt Header
    private static Map<String, Object> createJwtHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        return header;
    }

    // Jwt Claims
    private static Map<String, Object> createClaims(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("usrSeq", userDto.getUsrSeq());
        claims.put("usrEml", userDto.getUsrEml());
        claims.put("usrId", userDto.getUsrId());
        claims.put("usrTpCd", userDto.getUsrTpCd());
        return claims;
    }

    // Access-Token Expiration
    private static Date createAccessTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);
        return calendar.getTime();
    }

    // Refresh-Token Expiration
    private static Date createRefreshTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    // SigningKey
    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(keyConfiguration.getSecretKey());
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // Get Token(without Bearer)
    public static String getTokenFromBearer(String header) {
        return header.split(" ")[1];
    }

    /******************************************************************************
     * Token Validation METHODS
     *****************************************************************************/
    // Access-Token Validation
    public static boolean isValidToken(String token) {
        return checkClaims(token);
    }

    // Refresh-Token Validation
    public static boolean isValidRefreshToken(String token) {
        return checkClaims(token);
    }

    // Check Claims
    private static boolean checkClaims(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException | NullPointerException e) {
            return false;
        }
    }

    /******************************************************************************
     * Get Claims METHODS
     *****************************************************************************/
    // Claims From Token
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(keyConfiguration.getSecretKey()))
                .parseClaimsJws(token)
                .getBody();
    }

    // Get Email From claims
    public static String getEmailFromToken(String token) {
        return (String) getClaimsFromToken(token).get("usrEml");
    }

    // Get UserSeq From claims
    public static Long getSeqFromToken(String token) {
        return (Long) getClaimsFromToken(token).get("usrSeq");
    }

    // Get User Type From claims
    public static String getTypeFromToken(String token) {
        return (String) getClaimsFromToken(token).get("usrTpCd");
    }

    // Get User Id From claims
    public static String getIdFromToken(String token) {
        return (String) getClaimsFromToken(token).get("usrId");
    }
}
