package org.example.webtoonepics.public_method.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.public_method.dto.base.DefaultRes;
import org.example.webtoonepics.community.service.CommunityService;
import org.example.webtoonepics.jwt_login.dto.CustomUserDetails;
import org.example.webtoonepics.public_method.exception.ResponseMessage;
import org.example.webtoonepics.public_method.exception.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicService {

    private final CommunityService communityService;

    // 사용자 이메일 가져오기
    public String getUserEmail(CustomUserDetails userDetails, String providerId) {
        if (userDetails != null) {
            return userDetails.getUsername();
        } else if (providerId != null && !providerId.isEmpty()) {
            return communityService.findByProviderId(providerId);
        }
        return null;
    }


}
