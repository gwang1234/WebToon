package org.example.webtoonepics.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.dto.UserRequest;
import org.example.webtoonepics.user.dto.UserResponse;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 생성", description = "회원 가입")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
        User newUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "회원 수정", description = "회원 정보 수정")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User newUser = userService.updateUser(id, userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Operation(summary = "회원 삭제", description = "회원 탈퇴")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 조회", description = "회원의 이메일을 통해 정보 조회")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
        User user = userService.loginUser(userRequest);

        return ResponseEntity.ok().body(new UserResponse(user));
    }
}
