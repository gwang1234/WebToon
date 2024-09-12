package org.example.webtoonepics.controller;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.dto.JwtDto;
import org.example.webtoonepics.dto.JwtLoginDto;
import org.example.webtoonepics.jwt.JWTUtil;
import org.example.webtoonepics.service.CustomUserDetailsService;
import org.example.webtoonepics.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    // 회원가입
    @PostMapping("/jwt-auth")
    public ResponseEntity<String> auth(@RequestBody JwtDto jwtDto) {
        Boolean result = jwtService.auth(jwtDto);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no auth");
    }

    // jwt 토큰 인증
    @GetMapping("/jwt-token")
    public @ResponseBody String token() {
        return "abcd";
    }

    // 회원가입 - 이메일 인증
    @PostMapping("/auth-email")
    public ResponseEntity<Boolean> emailAuth(@RequestParam("email")String email) {
        Boolean exist = jwtService.existEmail(email);
        if (exist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    // 회원가입 - 유저네임 인증
    @PostMapping("/auth-username")
    public ResponseEntity<Boolean> usernameAuth(@RequestParam("userName")String userName) {
        Boolean exist = jwtService.existUsername(userName);
        if (exist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

}
