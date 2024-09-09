package org.example.webtoonepics.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
public class LoginController {
    
    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authorizeUrl;
    
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    
    @Value("${spring.security.oauth2.client.registration.kakao.scope}")
    private String scope;
    
    @GetMapping("/login/kakao")
    public String loginKakao() {
        return "redirect:%s?response_type=code&client_id=%s&redirect_uri=%s&scope=%s".formatted(authorizeUrl,
                clientId, redirectUri, scope);
    }
    
    @GetMapping("/login")
    public String loginKakao(@RequestParam boolean error) {
        
        if (error) {
            return "loginFail";
        } else {
            return "index";
        }
    }
}
