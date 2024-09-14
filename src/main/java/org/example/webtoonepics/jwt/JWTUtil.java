package org.example.webtoonepics.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private SecretKey secretKey; //JWT 토큰 객체 키를 저장할 시크릿 키

    public JWTUtil(@Value("${spring.jwt.secret}") String secret, RedisTemplate<String, Object> redisTemplate) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8)  , Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        this.redisTemplate = redisTemplate;
    }

    public String getEmail(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email",String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // access 토큰 생성
    public String createJwt(String email, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    // 리프레시 토큰 생성 및 Redis 저장
    public String generateRefreshToken(String email) {
        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7일 유효
                .signWith(secretKey)
                .compact();

        // Redis에 리프레시 토큰 저장 (유효 시간: 7일)
        redisTemplate.opsForValue().set(email, refreshToken, 7, TimeUnit.DAYS);

        return refreshToken;
    }

    // Refresh token 유효성 검증
    public boolean validateRefreshToken(String refreshToken) {
        try {
            // JWT를 파싱하여 유효성 검증
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken);
            return true;
        } catch (Exception e) {
            return false; // JWT 파싱 실패 시 유효하지 않은 토큰
        }
    }

    // Refresh token으로부터 이메일 추출
    public String getEmailFromRefreshToken(String refreshToken) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken).getPayload().getSubject();
    }

    // 로그아웃 시 리프레시 토큰 삭제
    public void deleteRefreshToken(String email) {
        redisTemplate.delete(email);
    }
}
