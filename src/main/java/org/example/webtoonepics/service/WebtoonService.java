package org.example.webtoonepics.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.dto.WebtoonDTO;
import org.example.webtoonepics.dto.WebtoonDTO.ItemList;
import org.example.webtoonepics.entity.Webtoon;
import org.example.webtoonepics.repository.WebtoonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebtoonService {

    private final WebtoonRepository webtoonRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${webtoon.api.key}")
    private String apiKey;

    // 외부 API에서 웹툰 목록을 가져오고 DB에 저장
    public Webtoon updateWebtoons(String provider) {

        int pageSize = 100; // 한 페이지에 가져올 아이템 수
        int totalCount = 0; // 총 데이터 수
        List<ItemList> allWebtoons = new ArrayList<>();

        try {
            // 첫 페이지 요청으로 totalCount 가져오기
            WebtoonDTO initialResponse = fetchWebtoonPage(provider, pageSize, 0);

            if (initialResponse != null) {
                totalCount = initialResponse.getResult().getTotalCount();
                List<ItemList> webtoonList = initialResponse.getItemList();
                if (webtoonList != null) {
                    allWebtoons.addAll(webtoonList);
                }

                // 나머지 페이지 요청
                for (int page = 100; page < totalCount; page += 100) {
                    WebtoonDTO response = fetchWebtoonPage(provider, pageSize, page);
                    if (response != null) {
                        webtoonList = response.getItemList();
                        if (webtoonList != null) {
                            allWebtoons.addAll(webtoonList);
                        }
                    }
                }

                log.info("총 웹툰 목록 크기: " + allWebtoons.size());

                // DB에 저장
                Webtoon latestWebtoon = null;
                for (ItemList webtoonItems : allWebtoons) {
                    Webtoon webtoon = webtoonItems.toEntity();

                    // ISBN으로 기존 웹툰 검색
                    Webtoon existingWebtoon = webtoonRepository.findByTitle(webtoon.getTitle());

                    if (existingWebtoon != null) {
                        // 기존 웹툰이 있으면 업데이트
                        existingWebtoon.setTitle(webtoon.getTitle());
                        existingWebtoon.setAuthor(webtoon.getAuthor());
                        existingWebtoon.setDescription(webtoon.getDescription());
                        existingWebtoon.setProvider(webtoon.getProvider());
                        latestWebtoon = webtoonRepository.save(existingWebtoon);
                    } else {
                        // 없으면 새로 저장
                        latestWebtoon = webtoonRepository.save(webtoon);
                    }
                }
                return latestWebtoon;
            } else {
                log.info("응답 데이터가 비어 있습니다.");
                return null;
            }

        } catch (WebClientResponseException e) {
            // WebClient 호출 중 에러 발생 시 처리
            log.error("API 호출 중 오류 발생: " + e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("오류 발생: " + e.getMessage());
        }
        return null;
    }

    private WebtoonDTO fetchWebtoonPage(String provider, int pageSize, int currentPage) {
        // 만화 규장각 URL

        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("www.kmas.or.kr")
                        .path("/openapi/search/bookAndWebtoonList")
                        .queryParam("prvKey", apiKey)
                        .queryParam("pltfomCdNm", provider)
                        .queryParam("viewItemCnt", pageSize)
                        .queryParam("pageNo", currentPage)  // 페이지 번호 추가
                        .build())
                .retrieve()
                .bodyToMono(WebtoonDTO.class)
                .block();
    }
}