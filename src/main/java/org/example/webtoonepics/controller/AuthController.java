package org.example.webtoonepics.controller;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.dto.KakaoUserInfoResponseDto;
import org.example.webtoonepics.service.KakaoLoginService;
import org.example.webtoonepics.service.KakaoOAuth2UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/oauth2")
@RestController
public class AuthController {

//    private final KakaoLoginService KakaoLoginService;
    
    /*
    @GetMapping("/kakao")
    public ResponseEntity<?> getCallback(@RequestParam String code) {
        
        // 카카오로부터 받은 authorization code로 Access Token을 가져옴
        String accessToken = KakaoLoginService.getAccessToken(code);
        // Access Token으로 사용자 정보 요청
        KakaoUserInfoResponseDto userInfo = KakaoLoginService.getUserInfo(accessToken);
        
        if (userInfo != null) {
            return ResponseEntity.ok(userInfo); // 사용자 정보를 JSON으로 반환
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Bad Request");
        }
        
        // User 로그인, 또는 회원가입 로직 추가?
        
    }*/
    
    @GetMapping("/oauth2/loginInfo")
    public String getLoginInfo(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        if (oAuth2User != null) {
            model.addAttribute("name", oAuth2User.getAttribute("name"));
            model.addAttribute("email", oAuth2User.getAttribute("email"));
            model.addAttribute("id", oAuth2User.getAttribute("id"));
        }
        return "loginInfo";
    }
    
    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "로그인 실패!";
    }
}
