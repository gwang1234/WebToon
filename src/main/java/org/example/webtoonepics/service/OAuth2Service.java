package org.example.webtoonepics.service;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

    public String getName(OAuth2User oAuth2User) {
        String name = oAuth2User.getAttribute("name");
        if (name == null) {
            name = (String) ((Map<String, Object>) oAuth2User.getAttribute("properties")).get("nickname");
        }
        return name;
    }

    public String getEmail(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            email = (String) ((Map<String, Object>) oAuth2User.getAttribute("kakao_account")).get("email");
        }
        return email;
    }
}
