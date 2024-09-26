package org.example.webtoonepics.question.controller;

import io.lettuce.core.dynamic.annotation.Param;
import org.example.webtoonepics.dto.CustomUserDetails;
import org.example.webtoonepics.question.dto.QuestionDto;
import org.example.webtoonepics.question.dto.QuestionListDto;
import org.example.webtoonepics.question.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qna")
public class QnaController {

    @Autowired
    QnaService qnaService;

    // 질문 게시판 글쓰기
    @PostMapping("/write")
    public ResponseEntity<String> write(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestBody QuestionDto dto) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String email = userDetails.getUsername();
        qnaService.writeQnA(email, dto);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    // 질문 게시판 목록
    @GetMapping("/list")
    public Page<QuestionListDto> list(@RequestParam(name = "page", defaultValue = "0")int page) {
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
        return qnaService.getQnaList(pageRequest);
    }

    // 질문 게시판 상세보기

    // 질문 게시판 수정
    @PatchMapping("/update")
    public ResponseEntity<String> update(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @RequestBody QuestionDto dto)
    {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String email = userDetails.getUsername();
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    // 질문 게시판 삭제

}
