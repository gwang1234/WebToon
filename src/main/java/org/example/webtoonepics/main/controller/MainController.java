package org.example.webtoonepics.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.webtoonepics.main.dto.MainWebtoonDto;
import org.example.webtoonepics.main.service.MainService;
import org.example.webtoonepics.webtoon.dto.WebtoonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    MainService mainService;

    // 좋아요 탑 10
    @Operation(summary = "좋아요 탑10 조회", description = "작품 제목, 이미지url 반환")
    @GetMapping("/api/top-like")
    public List<MainWebtoonDto> TopLike() {
        return mainService.findLikeTop10();
    }

    // 조회수 탑 10
    @Operation(summary = "조회수 탑10 조회", description = "작품 제목, 이미지url 반환")
    @GetMapping("/api/top-view")
    public List<MainWebtoonDto> TopView() {
        return mainService.findViewTop10();
    }

    // 좋아요
    @Operation(summary = "좋아요순 전체 조회", description = "좋아요로 전체 조회")
    @GetMapping("/api/likes")
    public List<MainWebtoonDto> likes(@RequestParam(value = "page", defaultValue = "0")int page) {
        return mainService.AllLikes(page);
    }

    // 조회수
    @Operation(summary = "조회수 전체 조회", description = "조회수로 전체 조회")
    @GetMapping("/api/views")
    public List<MainWebtoonDto> views(@RequestParam(value = "page", defaultValue = "0")int page) {
        return mainService.AllViews(page);
    }
}
