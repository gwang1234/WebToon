package org.example.webtoonepics.public_method;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.service.WebtoonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class WebtoonUpdate {

    private final WebtoonService webtoonService;

    @Scheduled(cron = "0 0 0 1, 16 * *") // 매달 1, 16일에 자동 업데이트
    public void updateWebtoons() {
        updateNaver();
        updateKakao();
    }

    @Async
    public void updateNaver() {
        try {
            log.info("네이버 웹툰 업데이트 시작");
            webtoonService.updateWebtoons("네이버웹툰");
            log.info("네이버 웹툰 업데이트 완료");
        } catch (Exception e) {
            // 오류 처리
            log.error("네이버 웹툰 업데이트 중 오류 발생", e);
        }
    }

    @Async
    public void updateKakao() {
        try {
            log.info("카카오 웹툰 업데이트 시작");
            webtoonService.updateWebtoons("카카오웹툰");
            log.info("카카오 웹툰 업데이트 완료");
        } catch (Exception e) {
            // 오류 처리
            log.error("카카오 웹툰 업데이트 중 오류 발생", e);
        }
    }


}
