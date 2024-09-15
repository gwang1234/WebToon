package org.example.webtoonepics.controller;


import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.entity.User;
import org.example.webtoonepics.repository.UserRepository;
import org.example.webtoonepics.service.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @GetMapping("/loginInfo")

    public String loginInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, Model model) {

        String email = (String) customOAuth2User.getUserNameAndEmail().get("email");
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null) {
            model.addAttribute("name", user.getUserName());
            model.addAttribute("email", user.getEmail());
            // 기타 사용자 정보를 추가할 수 있습니다.
        } else {
            model.addAttribute("name", "Unknown User");
        }

        return "loginInfo"; // 로그인 성공 페이지
    }

    @GetMapping("/loginFail")
    public String loginFail() {
        return "loginFail";
    }
}
