package org.example.webtoonepics.webtoon.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webtoonepics.community.dto.ProvideDto;
import org.example.webtoonepics.community.dto.base.DefaultRes;
import org.example.webtoonepics.community.exception.ResponseMessage;
import org.example.webtoonepics.community.exception.StatusCode;
import org.example.webtoonepics.community.service.CommunityService;
import org.example.webtoonepics.jwt_login.dto.CustomUserDetails;
import org.example.webtoonepics.webtoon.dto.WebtoonRequest;
import org.example.webtoonepics.webtoon.dto.WebtoonResponse;
import org.example.webtoonepics.webtoon.entity.Webtoon;
import org.example.webtoonepics.webtoon.service.LikewebtoonService;
import org.example.webtoonepics.webtoon.service.WebtoonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class WebtoonController {

    private final CommunityService communityService;
    private final WebtoonService webtoonService;
    private final LikewebtoonService likewebtoonService;

    @Operation(summary = "목록, 검색별 웹툰조회 ?searchKeyword=&searchType=title&page=0", description = "등록된 모든 웹툰 조회(NAVER, KAKAO)")
    @GetMapping("/webtoons")
    public ResponseEntity<?> findAllWebtoons(@RequestParam(value = "searchKeyword", required = false, defaultValue = "")String searchKeyword,
                                                                 @RequestParam(value = "searchType", defaultValue = "title")String searchType,
                                                                 @RequestParam(value = "page", defaultValue = "0")int page)
    {
        try {
            PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id"));
            Page<WebtoonResponse> list = webtoonService.getList(searchKeyword, searchType, pageRequest);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.SERCH_WRONG + e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @Operation(summary = "웹툰 좋아요 - 웹툰 좋아요 수 조회", description = "웹툰 좋아요 수")
    @GetMapping("/webtoons/{id}/like-webtoon")
    public ResponseEntity<Map<String, Long>> getLikes(@PathVariable(name = "id") Long webtoonId) {
        Long likesCount = likewebtoonService.WebtoonLikes(webtoonId);

        Map<String, Long> response = new HashMap<>();
        response.put("likeWebtoonCount", likesCount);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "웹툰 좋아요 - 사용자 중심 조회", description = "마이페이지 좋아요")
    @PostMapping("/webtoons/like-user")
    public ResponseEntity<?> getLikesUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @RequestBody(required = false) ProvideDto writeDto)
    {
        if (userDetails == null && (writeDto.getProvider_id() == null || writeDto.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (writeDto.getProvider_id() != null && !writeDto.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }

        try {
            List<Webtoon> list;
            if (userDetails != null) {
                list = likewebtoonService.getLikes(userDetails.getUsername());
            } else {
                String useremail = communityService.findByProviderId(writeDto.getProvider_id());
                list = likewebtoonService.getSocialLikes(useremail);
            }

            List<WebtoonResponse> webtoonResponses = list.stream().map(WebtoonResponse::new).toList();
            return new ResponseEntity<>(webtoonResponses, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "에러 발생: " + errorMessage));
        }
    }

    @Operation(summary = "웹툰 좋아요 - 좋아요 등록, 취소", description = "좋아요 등록")
    @PostMapping("/webtoons/{id}/like")
    public ResponseEntity<DefaultRes<String>> likeWebtoon(@PathVariable(name = "id") Long webtoonId,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @RequestBody(required = false) ProvideDto writeDto)
    {
        if (userDetails == null && (writeDto.getProvider_id() == null || writeDto.getProvider_id().isEmpty())) {
            return new ResponseEntity<>(DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.NO_AUTH), HttpStatus.UNAUTHORIZED);
        }
        if (userDetails != null && (writeDto.getProvider_id() != null && !writeDto.getProvider_id().isEmpty())) {
            SecurityContextHolder.clearContext();
            userDetails = null;
        }

        try {
            int likeResult;
            if (userDetails != null) {
                String email = userDetails.getUsername();
                likeResult = likewebtoonService.likeWebtoon(webtoonId, email);

            } else {
                String email = communityService.findByProviderId(writeDto.getProvider_id());
                likeResult = likewebtoonService.likeWebtoonSocial(webtoonId, email, writeDto.getProvider_id());
            }

            String status = likeResult > 0 ? "좋아요를 등록합니다." : "좋아요를 취소합니다.";
            return new ResponseEntity<>(DefaultRes.res(StatusCode.OK, status), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, "에러 발생: " + errorMessage));
        }

    }

    @Scheduled(cron = "0 0 0 1, 15 * *") // 매주 1, 15일에 자동 업데이트
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