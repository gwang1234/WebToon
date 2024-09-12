package org.example.webtoonepics.dto;

public interface OAuth2UserInfo {
    String getProvider();
    
    String getProviderId();
    
    String getEmail();
    
    String getName();
}
