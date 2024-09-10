//package org.example.webtoonepics.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.example.webtoonepics.dto.KakaoUserInfoResponseDto;
//import org.example.webtoonepics.dto.KakaoTokenResponseDto;
//import org.example.webtoonepics.exception.KaKaoUserInfoRequestException;
//import org.example.webtoonepics.exception.KakaoServerException;
//import org.example.webtoonepics.exception.KakaoTokenRequestException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class KakaoLoginService {
//
//    private final WebClient.Builder webClientBuilder;
//
//    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
//    private String clientId;
//    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
//    private String tokenUrl;
//    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
//    private String userInfoUrl;
//    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
//    private String grantType;
//    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
//    private String redirectUri;
//
//    // Access Token 가져오기
//    public String getAccessToken(String code) {
//        // Access Token 요청 (WebClient 사용)
//        KakaoTokenResponseDto tokenResponse = webClientBuilder.build()
//                .post()
//                .uri(tokenUrl)
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .body(BodyInserters.fromFormData("grant_type", grantType)
//                        .with("client_id", clientId)
//                        .with("redirect_uri", redirectUri)
//                        .with("code", code))
//                .retrieve() // 요청 실행
//                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(
//                        new KakaoTokenRequestException("Invalid authorization code or parameters")))
//                .onStatus(HttpStatusCode::is5xxServerError,
//                        clientResponse -> Mono.error(new KakaoServerException("Kakao server error")))
//                .bodyToMono(KakaoTokenResponseDto.class) // 응답을 String 으로 변환
//                .block(); // 동기식으로 결과 기다리기
//
//        log.info("[Kakao Service] Access Token: ====> {}", tokenResponse.getAccessToken());
//        log.info("[Kakao Service] Refresh Token ====> {}", tokenResponse.getRefreshToken());
//
//        //제공 조건: OpenID Connect가 활성화 된 앱의 토큰 발급 요청인 경우 또는 scope에 openid를 포함한 추가 항목 동의 받기 요청을 거친 토큰 발급 요청인 경우
//        log.info("[Kakao Service] Id Token ====> {}", tokenResponse.getIdToken());
//        log.info("[Kakao Service] Scope ====> {}", tokenResponse.getScope());
//
//        return tokenResponse.getAccessToken();
//    }
//
//    // 사용자 정보 가져오기
//    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
//        // 사용자 정보 요청 (WebClient 사용)
//        KakaoUserInfoResponseDto userInfo = webClientBuilder.build()
//                .post()
//                .uri(userInfoUrl)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .retrieve() // 요청 실행
//                .onStatus(HttpStatusCode::is4xxClientError,
//                        clientResponse -> Mono.error(new KaKaoUserInfoRequestException("Invalid Access Token")))
//                .onStatus(HttpStatusCode::is5xxServerError,
//                        clientResponse -> Mono.error(new KakaoServerException("Kakao server error")))
//                .bodyToMono(KakaoUserInfoResponseDto.class) // 응답을 String 으로 변환
//                .block(); // 동기식으로 결과 기다리기
//
//        log.info("[Kakao Service] Auth ID ====> {} ", userInfo.getId());
//        log.info("[Kakao Service] NickName ====> {} ", userInfo.getKakaoAccount().getProfile().getNickName());
//        log.info("[Kakao Service] ProfileImageUrl ====> {} ",
//                userInfo.getKakaoAccount().getProfile().getProfileImageUrl());
//
//        return userInfo;
//    }
//}
