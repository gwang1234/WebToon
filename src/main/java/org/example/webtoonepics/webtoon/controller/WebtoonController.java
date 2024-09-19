package org.example.webtoonepics.webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.service.WebtoonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class WebtoonController {

    private final WebtoonService webtoonService;

    // 외부 API에서 웹툰 데이터를 가져와 DB에 저장
    @GetMapping("/update/naver")
    public ResponseEntity<Webtoon> fetchWebtoonsNaver() {
        Webtoon webtoon = webtoonService.updateWebtoons("네이버웹툰");
        return ResponseEntity.ok().body(webtoon);
    }

    @PutMapping("/update/kakao")
    public String fetchWebtoonsKakao() {
        webtoonService.updateWebtoons("카카오웹툰");
        return "Webtoons fetched and saved successfully!";
    }
}
