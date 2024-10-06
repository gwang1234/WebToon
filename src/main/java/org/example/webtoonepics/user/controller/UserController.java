package org.example.webtoonepics.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.community.dto.base.DefaultRes;
import org.example.webtoonepics.community.exception.ResponseMessage;
import org.example.webtoonepics.community.exception.StatusCode;
import org.example.webtoonepics.community.service.CommunityService;
import org.example.webtoonepics.jwt_login.dto.CustomUserDetails;
import org.example.webtoonepics.user.dto.UserDeleteDto;
import org.example.webtoonepics.user.dto.UserRequest;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final CommunityService communityService;
    private final UserService userService;

    @Operation(summary = "회원 수정", description = "회원 정보 수정")
    @PutMapping("/users")
    public ResponseEntity<DefaultRes<String>> modifyUser(@RequestBody UserRequest userRequest
                                            ,@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null && (userRequest.getProvider_id() == null || userRequest.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (userRequest.getProvider_id() != null && !userRequest.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }
        try {
            // 사용자 이메일 가져오기
            String useremail;
            if (userDetails != null) {
                useremail = userDetails.getUsername();
            } else {
                useremail = communityService.findByProviderId(userRequest.getProvider_id());
            }
            System.out.println(useremail);

            userService.updateUser(useremail, userRequest);
            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "에러 발생: " + errorMessage));
        }
    }

    @Operation(summary = "회원 삭제", description = "회원 탈퇴")
    @DeleteMapping("/users")
    public ResponseEntity<DefaultRes<String>> deleteUser(@RequestBody(required = false) UserDeleteDto userRequest
                                            ,@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null && (userRequest.getProvider_id() == null || userRequest.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (userRequest.getProvider_id() != null && !userRequest.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }
        try {
            // 사용자 이메일 가져오기
            String useremail;
            if (userDetails != null) {
                useremail = userDetails.getUsername();
            } else {
                useremail = communityService.findByProviderId(userRequest.getProvider_id());
            }
            userService.deleteUser(useremail, userRequest.getProvider_id());
            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "에러 발생: " + errorMessage));
        }

    }

    @Operation(summary = "회원 조회", description = "회원의 아이디를 통해 정보 조회")
    @GetMapping("/users")
    public ResponseEntity<?> loginUser(@RequestBody UserDeleteDto userRequest
                                            ,@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null && (userRequest.getProvider_id() == null || userRequest.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (userRequest.getProvider_id() != null && !userRequest.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
        }

        User user = userService.readUser(userRequest);
        return ResponseEntity.ok().body(user);
    }
}
