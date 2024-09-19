package org.example.webtoonepics.controller;


import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.example.webtoonepics.user.service.CustomOAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @GetMapping("/OAuth2/loginInfo")
    public ResponseEntity<?> loginInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, Model model) {

        String email = (String) customOAuth2User.getUserNameAndEmail().get("email");
        User user = userRepository.findByEmail(email).orElse(null);

        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            response.put("name", user.getUserName());
            response.put("email", user.getEmail());
            // 기타 사용자 정보를 추가하려면 하세요~
        } else {
            response.put("name", "Unknown User");
        }

        return ResponseEntity.ok(response);
    }
}
