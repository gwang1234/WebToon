package org.example.webtoonepics.controller;


import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.service.OAuth2Service;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
@RequiredArgsConstructor
public class LoginController {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/loginInfo")

    public String loginInfo(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {

        model.addAttribute("nickname", oAuth2Service.getName(oAuth2User));
        model.addAttribute("email", oAuth2Service.getEmail(oAuth2User));

        return "loginInfo";
    }

    @GetMapping("/loginFail")
    public String loginFail() {
        return "loginFail";
    }
}
