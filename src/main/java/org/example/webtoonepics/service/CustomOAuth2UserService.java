package org.example.webtoonepics.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.entity.Role;
import org.example.webtoonepics.entity.User;
import org.example.webtoonepics.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws AuthenticationException {

        // 사용자 정보를 가져옵니다.
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 클라이언트 등록 ID를 가져옵니다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 사용자 정보를 처리하는 로직
        Map<String, Object> userInfo = extractUserInfo(registrationId, oAuth2User.getAttributes());
        String email = (String) userInfo.get("email");
        String userName = (String) userInfo.get("name");

        // 기존 회원 여부
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            // 신규 회원이면 저장
            User user = User.builder()
                    .email(email)
                    .userName(userName)
                    .provider(registrationId)
                    .role(Role.ROLE_USER)
                    .build();

            // 사용자 저장 로직 추가
            userRepository.save(user);
        }

        return new CustomOAuth2User(oAuth2User, registrationId);
    }

    public static Map<String, Object> extractUserInfo(String registrationId, Map<String, Object> attributes) {
        Map<String, Object> resultMap = new HashMap<>();

        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            resultMap.put("name", ((Map<String, Object>) attributes.get("properties")).get("nickname"));
            resultMap.put("email", kakaoAccount.get("email"));
        } else if ("google".equals(registrationId)) {
            resultMap.put("name", attributes.get("name"));
            resultMap.put("email", attributes.get("email"));
        } else {
            throw new IllegalArgumentException("Unsupported registration ID: " + registrationId);
        }

        return resultMap;
    }
}
