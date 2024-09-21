package org.example.webtoonepics.webtoon.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.webtoon.dto.WebtoonRequest;
import org.example.webtoonepics.webtoon.dto.WebtoonResponse;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.service.WebtoonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @GetMapping("/webtoons")
    public ResponseEntity<List<WebtoonResponse>> getWebtoons() {
        List<WebtoonResponse> webtoonList = webtoonService.findAll().stream().map(WebtoonResponse::new).toList();
    return new ResponseEntity<>(webtoonList, HttpStatus.OK);
    }

    @PutMapping("/webtoons/naver")
    public ResponseEntity<WebtoonRequest.Result> addWebtoonsNaver() {
        WebtoonRequest request = webtoonService.updateWebtoons("네이버웹툰");
        return ResponseEntity.ok().body(request.getResult());
    }

    @PutMapping("/webtoons/kakao")
    public ResponseEntity<WebtoonRequest.Result> addWebtoonsKakao() {
        WebtoonRequest request = webtoonService.updateWebtoons("카카오웹툰");
        return ResponseEntity.ok().body(request.getResult());
    }
}
