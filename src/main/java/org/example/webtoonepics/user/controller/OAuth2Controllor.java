package org.example.webtoonepics.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.jwt_login.jwt.JWTUtil;
import org.example.webtoonepics.jwt_login.service.JwtService;
import org.example.webtoonepics.user.service.CustomOAuth2UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuth2Controllor {

    private final CustomOAuth2UserService auth2UserService;
    private final JWTUtil jwtUtil;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final JwtService jwtService;

    @GetMapping("/oauth2/login")
    public void login(OAuth2AuthenticationToken authentication, HttpServletResponse response) throws IOException {

        // OAuth2 프로바이더에서 제공하는 사용자 이름
        String username = authentication.getName();
        String role = jwtService.findRoleByProviderId(username);

        // JWT 생성
        String accessToken = jwtUtil.createJwt(username, role, 60 * 60 * 1000L);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        // JWT를 쿠키에 저장
        response.addHeader(HttpHeaders.SET_COOKIE, "accessToken=" + accessToken + "; HttpOnly; Secure; Path=/; Max-Age=900"); // 15분
        response.addHeader(HttpHeaders.SET_COOKIE, "refreshToken=" + refreshToken + "; HttpOnly; Secure; Path=/; Max-Age=604800"); // 7일

        log.info("accessToken=" + accessToken + "; HttpOnly; Secure; Path=/; Max-Age=900");
        
        response.sendRedirect("/");
    }
}
