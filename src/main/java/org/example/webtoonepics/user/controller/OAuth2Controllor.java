package org.example.webtoonepics.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.dto.UserResponse;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.service.CustomOAuth2User;
import org.example.webtoonepics.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuth2Controllor {

    private final UserService userService;

    @GetMapping("/OAuth2/loginInfo")
    public ResponseEntity<UserResponse> loginInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        String email = (String) customOAuth2User.getUserNameAndEmail().get("email");
        User user = userService.findByUser(email);

        return ResponseEntity.ok().body(new UserResponse(user));
    }
}
