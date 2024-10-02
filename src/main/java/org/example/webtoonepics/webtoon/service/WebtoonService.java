package org.example.webtoonepics.webtoon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.webtoon.dto.WebtoonRequest;
import org.example.webtoonepics.webtoon.dto.WebtoonRequest.ItemList;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.repository.WebtoonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<Webtoon> findAll() {
        return webtoonRepository.findAll();
    }

    public Webtoon findById(Long id) {
        return webtoonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Webtoon not found. => " + id));
    }


    // 외부 API에서 웹툰 목록을 가져오고 DB에 저장
    @Transactional
    public WebtoonRequest updateWebtoons(String provider) {

        int pageSize = 100; // 한 페이지에 가져올 아이템 수
        int totalCount = 0; // 총 데이터 수
        List<ItemList> allWebtoons = new ArrayList<>();

        try {
            // 첫 페이지 요청으로 totalCount 가져오기
            WebtoonRequest initialResponse = fetchWebtoonPage(provider, pageSize, 0);

            if (initialResponse == null) {
                log.warn("응답 데이터가 비어 있습니다.");
                return null;
            }

            totalCount = initialResponse.getResult().getTotalCount();
            allWebtoons.addAll(initialResponse.getItemList());

            // 나머지 데이터 가져오기
            for (int page = 100; page < totalCount; page += 100) {
                WebtoonRequest response = fetchWebtoonPage(provider, pageSize, page);
                if (response != null && response.getItemList() != null) {
                    allWebtoons.addAll(response.getItemList());
                }
            }

            log.info("총 웹툰 목록 크기: {}", allWebtoons.size());

            // DB에 저장
            saveOrUpdateWebtoons(allWebtoons, provider);
            return initialResponse;

        } catch (WebClientResponseException e) {
            // WebClient 호출 중 에러 발생 시 처리
            log.error("API 호출 중 오류 발생: " + e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            log.error("오류 발생: " + e.getMessage());
        }
        return null;
    }

    @Transactional
    protected void saveOrUpdateWebtoons(List<ItemList> allWebtoons, String provider) {
        List<Webtoon> newWebtoons = new ArrayList<>();
        List<Webtoon> updateWebtoons = new ArrayList<>();

        // 기존 웹툰 모두 조회
        List<Webtoon> existingWebtoons = webtoonRepository.findByProvider(provider);

        // 기존 웹툰의 제목을 Map으로 변환함
        Map<String, Webtoon> existingWebtoonMap = existingWebtoons.stream()
                .collect(Collectors.toMap(Webtoon::getTitle, w -> w));

        // 웹툰을 반복하면서 새 웹툰과 업데이트할 웹툰을 구분
        for (ItemList webtoonItems : allWebtoons) {
            Webtoon newWebtoon = webtoonItems.toEntity();
            Webtoon existingWebtoon = existingWebtoonMap.get(newWebtoon.getTitle());

            if (existingWebtoon != null) {
                existingWebtoon.updateWith(newWebtoon);
                updateWebtoons.add(existingWebtoon);
            } else {
                newWebtoons.add(newWebtoon);
            }
        }

        if (!newWebtoons.isEmpty()) {
            webtoonRepository.saveAll(newWebtoons);
        }
        if (!updateWebtoons.isEmpty()) {
            webtoonRepository.saveAll(updateWebtoons);
        }
    }


    private WebtoonRequest fetchWebtoonPage(String provider, int pageSize, int currentPage) {
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
                .bodyToMono(WebtoonRequest.class)
                .block();
    }
}