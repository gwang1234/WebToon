package org.example.webtoonepics.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webtoonepics.dto.CustomUserDetails;
import org.example.webtoonepics.entity.Role;
import org.example.webtoonepics.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil, RedisTemplate<String, Object> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        // 디버깅을 위해 Authorization 헤더 값 출력
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Authorization Header: " + authorization);

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("no access token");
            filterChain.doFilter(request, response); // 필터 종료

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰에서 email과 role 획득
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        System.out.println(role);


        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
//            String refreshToken = (String) redisTemplate.opsForValue().get("refreshToken: " + email);
//
//            if (refreshToken != null && jwtUtil.validateRefreshToken(email, refreshToken)) {
//                String newAccessToken = jwtUtil.createJwt(email, role, 60 * 60 * 1000L);
//                String newRefreshToken = jwtUtil.generateRefreshToken(email);
//                response.addHeader("Authorization", "Bearer " + newAccessToken);
//                response.addHeader("Refresh-Token", newRefreshToken);
//                filterChain.doFilter(request, response);
//                return;
//            } else {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
        }

        //user를 생성하여 값 set
        User userEntity = new User();
        userEntity.setEmail(email);
        userEntity.setPassword("pppassword");
        userEntity.setRole(Role.valueOf(role));

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

         //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
