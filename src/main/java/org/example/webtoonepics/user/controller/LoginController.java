package org.example.webtoonepics.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.dto.UserResponse;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @Operation(summary = "회원조회", description = "회원의 이메일을 통해 정보 조회")
    @GetMapping("/loginInfo")
    public ResponseEntity<UserResponse> loginInfo(String email) {

        User user = userService.findByUser(email);

        return ResponseEntity.ok().body(new UserResponse(user));
    }
}
