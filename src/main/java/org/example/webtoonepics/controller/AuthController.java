package org.example.webtoonepics.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/oauth2")
@Controller
public class AuthController {
    @Value("${oauth.kakao.client-id}")
    private String clientId;
    
    @Value("${oauth.kakao.redirect-url}")
    private String redirectUri;
    
    @Value("${oauth.kakao.authorize-url}")
    private String authorizeUrl;
    
    @GetMapping("/kakao")
    public String getAuthorize(@RequestParam(required = false) String code) {
        
        if (code == null) {
            return "redirect:https://%s?client_id=%s&redirect_uri=%s&response_type=code".formatted(authorizeUrl, clientId, redirectUri);
        } else {
            System.out.println(code);
            return "login"; // 로그인 성공시 redirect url
        }
    }
}
