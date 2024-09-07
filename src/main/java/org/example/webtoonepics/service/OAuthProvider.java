package org.example.webtoonepics.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.domain.KaKaoAccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OAuthProvider {
    
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String TOKEN_TYPE = "Bearer ";
    
    private final RestTemplate restTemplate;
    
    @Value("${oauth.kakao.token-url}")
    private String tokenUri;
    
    @Value("${oauth.kakao.user-info-url}")
    private String userInfoUri;
    
    @Value("${oauth.kakao.redirect-url}")
    private String redirectUri;
    
    @Value("${oauth.kakao.client-id}")
    private String clientId;
    
    public KaKaoAccessTokenResponse getAccessToken(String authorizationCode) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", GRANT_TYPE);
            body.add("client_id", clientId);
            body.add("redirect_uri", redirectUri);
            body.add("code", authorizationCode);
            
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
            
            return restTemplate.postForEntity(tokenUri, request, KaKaoAccessTokenResponse.class).getBody();
        } catch (HttpClientErrorException e) {
//            throw new KakaoTokenRequestException(e.getMessage());
        } catch (HttpServerErrorException e) {
//            throw new KakaoServerException(e.getMessage());
        }
        return null;
    }
}