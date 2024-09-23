package org.example.webtoonepics.community.controller;

import com.nimbusds.oauth2.sdk.Response;
import org.example.webtoonepics.community.dto.C_commentDto;
import org.example.webtoonepics.community.dto.C_commentWriteDto;
import org.example.webtoonepics.community.service.CommentService;
import org.example.webtoonepics.community.service.CommunityService;
import org.example.webtoonepics.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/c-comment")
public class CommunityCommentController {

    @Autowired
    CommentService commentService;

    // 댓글 쓰기
    @PostMapping("/write/{CommunityId}")
    public ResponseEntity<String> write(@PathVariable Long CommunityId,
                                   @RequestBody C_commentWriteDto writeDto,
                                   @AuthenticationPrincipal CustomUserDetails userDetails)
    {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String email = userDetails.getUsername();
        commentService.writeComment(writeDto, email, CommunityId);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    // 댓글 보여주기
    @GetMapping("/{CommunityId}")
    public Page<C_commentDto> showComment(@PathVariable Long CommunityId,
                                          @RequestParam(value = "page", defaultValue = "0")int page)
    {
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
        return commentService.showComment(CommunityId, pageRequest);
    }

    // 댓글 수정
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
