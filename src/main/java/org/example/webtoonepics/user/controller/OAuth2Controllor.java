package org.example.webtoonepics.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.dto.UserResponse;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.service.CustomOAuth2User;
import org.example.webtoonepics.user.service.CustomOAuth2UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuth2Controllor {

    private final CustomOAuth2UserService auth2UserService;

    @GetMapping("/oauth2/loginInfo")
    public ResponseEntity<UserResponse> loginInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        String providerId = (String) customOAuth2User.getAttribute("provider_id");
        User user = auth2UserService.findByProviderId(providerId);

        return ResponseEntity.ok().body(new UserResponse(user));
    }
}
