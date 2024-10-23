package org.example.webtoonepics.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.webtoonepics.community.dto.C_commentDto;
import org.example.webtoonepics.community.dto.C_commentWriteDto;
import org.example.webtoonepics.community.dto.ProvideDto;
import org.example.webtoonepics.public_method.dto.base.DefaultRes;
import org.example.webtoonepics.public_method.exception.ResponseMessage;
import org.example.webtoonepics.public_method.exception.StatusCode;
import org.example.webtoonepics.community.service.CommentService;
import org.example.webtoonepics.community.service.CommunityService;
import org.example.webtoonepics.jwt_login.dto.CustomUserDetails;
import org.example.webtoonepics.public_method.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/c-comment")
public class CommunityCommentController {

    @Autowired
    CommunityService communityService;

    @Autowired
    CommentService commentService;

    @Autowired
    PublicService publicService;

    // 댓글 쓰기
    @Operation(summary = "커뮤니티 댓글 작성", description = "header로 로그인 정보를 받아와서 해당 게시판 댓글 등록")
    @PostMapping("/write/{CommunityId}")
    public ResponseEntity<?> write(@PathVariable Long CommunityId,
                                   @RequestBody(required = false) C_commentWriteDto writeDto,
                                   @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        try {
            if (userDetails == null && (writeDto.getProvider_id() == null || writeDto.getProvider_id().isEmpty())) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
            }
            if (userDetails != null && (writeDto.getProvider_id() != null && !writeDto.getProvider_id().isEmpty())) {
                SecurityContextHolder.clearContext();
                userDetails = null;
            }

            // 사용자 이메일 가져오기
            String userEmail = publicService.getUserEmail(userDetails, writeDto.getProvider_id());

            commentService.writeComment(writeDto, userEmail, CommunityId);
            return ResponseEntity.status(HttpStatus.CREATED).body("ok");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 댓글 보여주기
    @Operation(summary = "커뮤니티 댓글 조회", description = "게시판 댓글 조회")
    @GetMapping("/{CommunityId}")
    public ResponseEntity<?> showComment(@PathVariable Long CommunityId,
                                          @RequestParam(value = "page", defaultValue = "0")int page)
    {
        try {
            PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id"));
            Page<C_commentDto> cCommentDtos = commentService.showComment(CommunityId, pageRequest);
            return new ResponseEntity<>(cCommentDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 댓글 수정
    @Operation(summary = "커뮤니티 댓글 수정", description = "header로 로그인 정보를 받아와서 해당 게시판 댓글 수정")
    @PutMapping("/update/{id}")
    public ResponseEntity<DefaultRes<String>> updateComment(@PathVariable Long id,
                                           @RequestBody(required = false) C_commentWriteDto writeDto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        try {
            if (userDetails == null && (writeDto.getProvider_id() == null || writeDto.getProvider_id().isEmpty())) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
            }
            if (userDetails != null && (writeDto.getProvider_id() != null && !writeDto.getProvider_id().isEmpty())) {
                SecurityContextHolder.clearContext();
                userDetails = null;
            }

            // 사용자 이메일 가져오기
            String userEmail = publicService.getUserEmail(userDetails, writeDto.getProvider_id());

            commentService.updateComment(id, writeDto, userEmail);
            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 댓글 삭제
    @Operation(summary = "커뮤니티 댓글 삭제", description = "header로 로그인 정보를 받아와서 해당 게시판 댓글 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DefaultRes<String>> deleteComment(@PathVariable Long id,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails,
                                                            @RequestBody(required = false) ProvideDto writeDto)
    {
        try {
            if (userDetails == null && (writeDto.getProvider_id() == null || writeDto.getProvider_id().isEmpty())) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
            }
            if (userDetails != null && (writeDto.getProvider_id() != null && !writeDto.getProvider_id().isEmpty())) {
                SecurityContextHolder.clearContext();
                userDetails = null;
            }

            // 사용자 이메일 가져오기
            String userEmail = publicService.getUserEmail(userDetails, writeDto.getProvider_id());

            commentService.deleteComment(id, userEmail);
            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 사용자 댓글 조회
    @Operation(summary = "커뮤니티 댓글 사용자 중심 조회")
    @PostMapping("/user/comment")
    public ResponseEntity<?> UserComment(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @RequestBody(required = false) ProvideDto provideDto,
                                                @RequestParam(value = "page", defaultValue = "0")int page)
    {
        if (userDetails == null && (provideDto.getProvider_id() == null || provideDto.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (provideDto.getProvider_id() != null && !provideDto.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }

        try {
            String email = publicService.getUserEmail(userDetails, provideDto.getProvider_id());

            PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id"));
            Page<C_commentDto> list = commentService.getUserList(email, pageRequest);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
