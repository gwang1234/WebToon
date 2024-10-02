package org.example.webtoonepics.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.webtoonepics.community.dto.C_commentDto;
import org.example.webtoonepics.community.dto.C_commentWriteDto;
import org.example.webtoonepics.community.dto.base.DefaultRes;
import org.example.webtoonepics.community.exception.ResponseMessage;
import org.example.webtoonepics.community.exception.StatusCode;
import org.example.webtoonepics.community.service.CommentService;
import org.example.webtoonepics.jwt_login.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/c-comment")
public class CommunityCommentController {

    @Autowired
    CommentService commentService;

    // 댓글 쓰기
    @Operation(summary = "커뮤니티 댓글 작성", description = "header로 로그인 정보를 받아와서 해당 게시판 댓글 등록")
    @PostMapping("/write/{CommunityId}")
    public ResponseEntity<?> write(@PathVariable Long CommunityId,
                                   @RequestBody C_commentWriteDto writeDto,
                                   @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        try {
            if (userDetails == null) {
                return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
            }
            String email = userDetails.getUsername();
            commentService.writeComment(writeDto, email, CommunityId);
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
    public Page<C_commentDto> showComment(@PathVariable Long CommunityId,
                                          @RequestParam(value = "page", defaultValue = "0")int page)
    {
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
        return commentService.showComment(CommunityId, pageRequest);
    }

    // 댓글 수정
    @Operation(summary = "커뮤니티 댓글 수정", description = "header로 로그인 정보를 받아와서 해당 게시판 댓글 수정")
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id,
                                           @RequestBody C_commentWriteDto writeDto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String email = userDetails.getUsername();
        commentService.updateComment(id, writeDto, email);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }


    // 댓글 삭제
    @Operation(summary = "커뮤니티 댓글 삭제", description = "header로 로그인 정보를 받아와서 해당 게시판 댓글 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id,
                                                @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String email = userDetails.getUsername();
        commentService.deleteComment(id, email);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

}
