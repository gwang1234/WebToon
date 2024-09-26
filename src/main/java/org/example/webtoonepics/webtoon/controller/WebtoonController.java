package org.example.webtoonepics.webtoon.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.webtoon.dto.WebtoonRequest;
import org.example.webtoonepics.webtoon.dto.WebtoonResponse;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.service.LikewebtoonService;
import org.example.webtoonepics.webtoon.service.WebtoonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class WebtoonController {

    private final WebtoonService webtoonService;
    private final LikewebtoonService likewebtoonService;

    @Operation(summary = "웹툰조회", description = "등록된 모든 웹툰 조회(NAVER, KAKAO)")
    @GetMapping("/webtoons")
    public ResponseEntity<List<WebtoonResponse>> findAllWebtoons() {
        List<WebtoonResponse> webtoonList = webtoonService.findAll().stream().map(WebtoonResponse::new).toList();
        return new ResponseEntity<>(webtoonList, HttpStatus.OK);
    }

    @Operation(summary = "특정 웹툰조회", description = "id값으로 웹툰 조회")
    @GetMapping("/webtoons/{id}")
    public ResponseEntity<WebtoonResponse> findWebtoon(@PathVariable(name = "id") Long id) {
        Webtoon webtoon = webtoonService.findById(id);
        return ResponseEntity.ok().body(new WebtoonResponse(webtoon));
    }

    @Operation(summary = "네이버 웹툰 DB 업데이트", description = "1 ~ 3분 정도 기다림이 있습니다.")
    @PutMapping("/webtoons/naver")
    public ResponseEntity<WebtoonRequest.Result> fetchWebtoonsNaver() {
        WebtoonRequest request = webtoonService.updateWebtoons("네이버웹툰");
        return ResponseEntity.ok().body(request.getResult());
    }

    @Operation(summary = "카카오 웹툰 DB 업데이트", description = "1 ~ 3분 정도 기다림이 있습니다.")
    @PutMapping("/webtoons/kakao")
    public ResponseEntity<WebtoonRequest.Result> fetchWebtoonsKakao() {
        WebtoonRequest request = webtoonService.updateWebtoons("카카오웹툰");
        return ResponseEntity.ok().body(request.getResult());
    }

    @GetMapping("/webtoons/{id}/like")
    public ResponseEntity<Map<String, Long>> getLikes(@PathVariable(name = "id") Long webtoonId, @RequestParam(name = "id") Long userId) {
        Long likesCount = likewebtoonService.getLikes(webtoonId, userId);

        Map<String, Long> response = new HashMap<>();
        response.put("webtoon_id", likesCount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/webtoons/{id}/like")
    public ResponseEntity<String> likeWebtoon(@PathVariable(name = "id") Long webtoonId, @RequestParam(name = "id") Long userId) {
        likewebtoonService.likeWebtoon(webtoonId, userId);
        return ResponseEntity.ok(userId + " : 좋아요가 등록되었습니다.");
    }
}
