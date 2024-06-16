package com.turtledoctor.kgu.auth.jwt;


import com.turtledoctor.kgu.auth.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.turtledoctor.kgu.exception.ErrorCode.INVALID_JWT_SIGNATURE;

@Component
@Slf4j
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String createJwt(String kakaoId, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("kakaoId", kakaoId)
//                .claim("name", name) // 제거
//                .claim("email", email) // 제거
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    private Claims getClaims(String token) {
        try {
            //jwts의 deprecated 된 코드 부분 수정
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build().parseSignedClaims(token).getPayload();
        } catch (SignatureException e) {
            log.error("JWT signature error: {}", e.getMessage());
            throw new AuthException(INVALID_JWT_SIGNATURE);
        } catch (Exception e) {
            log.error("JWT parsing error: {}", e.getMessage());
            throw new AuthException(INVALID_JWT_SIGNATURE);
        }
    }

    public String getkakaoId(String token) {
        return getClaims(token).get("kakaoId", String.class);
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

//    public String getEmail(String token) { // 추가
//        return getClaims(token).get("email", String.class);
//    }

//    public String getName(String token) { // 추가
//        return getClaims(token).get("name", String.class);
//    }

    public Boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public void validateToken(String token) {
        getClaims(token); // This will throw an exception if the token is invalid
    }
}
