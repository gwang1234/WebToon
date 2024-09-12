//package org.example.webtoonepics.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.util.Collections;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class KakaoOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
//    private String use1rInfoEndpointUri;
//
//    private final WebClient webClient;
//
//    @Override
//    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws AuthenticationException {
//        String accessToken = userRequest.getAccessToken().getTokenValue();
//        return fetchUserAttributes(accessToken).map(this::createOAuth2User);
//    }
//
//    private Mono<Map<String, Object>> fetchUserAttributes(String accessToken) {
//        return webClient.get()
//                .uri(use1rInfoEndpointUri)
//                .headers(headers -> headers.setBearerAuth(accessToken))
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
//                });
//    }
//
//    private OAuth2User createOAuth2User(Map<String, Object> attributes) {
//        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
//        String email = (String) kakaoAccount.get("email");
//        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
//        String nickname = (String) profile.get("nickname");
//
//        // 필요한 추가 로직 또는 사용자 정보 가공
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                attributes,
//                "id" // 기본 사용자 식별 필드
//        );
//    }
//}
//
