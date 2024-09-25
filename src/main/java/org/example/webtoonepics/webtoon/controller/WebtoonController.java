package org.example.webtoonepics.webtoon.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.webtoon.dto.WebtoonRequest;
import org.example.webtoonepics.webtoon.dto.WebtoonResponse;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.service.WebtoonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class WebtoonController {

    private final WebtoonService webtoonService;

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
}
