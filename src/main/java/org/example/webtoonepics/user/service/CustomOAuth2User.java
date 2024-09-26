package org.example.webtoonepics.user.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User, Serializable {

    private final OAuth2User oAuth2User;
    private final String registrationId;

    public CustomOAuth2User(OAuth2User oAuth2User, String registrationId) {
        this.oAuth2User = oAuth2User;
        this.registrationId = registrationId;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public Map<String, Object> getUserNameAndEmail() {
        return CustomOAuth2UserService.extractUserInfo(registrationId, getAttributes());
    }
}
