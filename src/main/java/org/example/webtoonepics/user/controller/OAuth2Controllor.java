package org.example.webtoonepics.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.user.dto.UserResponse;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.service.CustomOAuth2User;
import org.example.webtoonepics.user.service.CustomOAuth2UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuth2Controllor {

    private final CustomOAuth2UserService auth2UserService;

    @GetMapping("/oauth2/loginInfo")
    public ResponseEntity<?> loginInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        String providerId = customOAuth2User.getName();
        User user = auth2UserService.findByProviderId(providerId);

        return ResponseEntity.ok().body(new UserResponse(user));
    }
}
