package org.example.webtoonepics.jwt_login.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.jwt_login.dto.JwtDto;
import org.example.webtoonepics.jwt_login.jwt.JWTUtil;
import org.example.webtoonepics.jwt_login.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    // 회원가입
    @Operation(summary = "jwt 회원가입", description = "이메일, 비밀번호, 닉네임을 받고 회원 등록")
    @PostMapping("/jwt-auth")
    public ResponseEntity<String> auth(@RequestBody JwtDto jwtDto) {
        Boolean result = jwtService.auth(jwtDto);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no auth");
    }

    // jwt 토큰 인증 - 테스트용
    @Operation(summary = "jwt 테스트", description = "프론트는 쓸일이 없다. 서버에서 테스트 용도")
    @GetMapping("/jwt-token")
    public @ResponseBody String token() {
        return "abcd";
    }

    // 리프레시 토큰
    @Operation(summary = "jwt 리프레시 토큰", description = "리프레시, 액세스 토큰 재발급")
    @PostMapping("/jwt-refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {

        if (refreshToken == null || !jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }

        // Refresh 토큰에서 사용자 정보 추출
        String email = jwtUtil.getEmailFromRefreshToken(refreshToken);
        String role = jwtService.findRoleByEmail(email);

        // 새로운 Access 토큰 생성
        String newAccessToken = jwtUtil.createJwt(email, role, 60 * 60 * 1000L);
        String newRefreshToken = jwtUtil.generateRefreshToken(email);

        // 클라이언트로 새로운 Access 토큰 반환
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newAccessToken)
                .header("Refresh-Token", newRefreshToken)
                .body("Access Token Updated");
    }

    // 회원가입 - 이메일 인증
    @Operation(summary = "jwt 회원가입 이메일 인증", description = "중복된 이메일이 있는지 확인")
    @PostMapping("/auth-email")
    public ResponseEntity<Boolean> emailAuth(@RequestParam("email")String email) {
        Boolean exist = jwtService.existEmail(email);
        if (exist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    // 회원가입 - 유저네임 인증
    @Operation(summary = "jwt 회원가입 유저네임 인증", description = "중복된 유저네임이 있는지 확인")
    @PostMapping("/auth-username")
    public ResponseEntity<Boolean> usernameAuth(@RequestParam("userName")String userName) {
        Boolean exist = jwtService.existUsername(userName);
        if (exist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}
