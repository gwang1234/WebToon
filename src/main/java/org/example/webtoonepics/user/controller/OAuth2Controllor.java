package org.example.webtoonepics.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.jwt_login.jwt.JWTUtil;
import org.example.webtoonepics.jwt_login.service.JwtService;
import org.example.webtoonepics.user.service.CustomOAuth2UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        // OAuth2 사용자 정보 추출
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        String email = null;

        // OAuth2 프로바이더에 따른 이메일 정보 추출
        if (authentication.getAuthorizedClientRegistrationId().equals("google")) {
            // 구글의 경우
            email = (String) attributes.get("email");
        } else if (authentication.getAuthorizedClientRegistrationId().equals("kakao")) {
            // 카카오의 경우
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
        }

        String role = jwtService.findRoleByProviderId(username);

//        // JWT 생성
//        String accessToken = jwtUtil.createJwt(username, role, 60 * 60 * 1000L);
//        String refreshToken = jwtUtil.generateRefreshToken(username);
//
//        // JWT 헤더 등록
//        response.addHeader("Authorization", "Bearer " + accessToken);
//        response.addHeader("Refresh-Token", refreshToken);
//
//        // 로그인 성공 후 JWT 저장
        String redirectUrl = "http://webtoonepic.site:3000?username=" + username + "&email=" + email;
        response.sendRedirect(redirectUrl);

    }
}
