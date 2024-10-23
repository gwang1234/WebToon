package org.example.webtoonepics.user.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.public_method.exception.ResponseMessage;
import org.example.webtoonepics.user.dto.UserDeleteDto;
import org.example.webtoonepics.user.dto.UserPwdDto;
import org.example.webtoonepics.user.dto.UserRequest;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public User loginUser(UserRequest userRequest) {
        User user = userRepository.findByEmailAndProviderId(userRequest.getEmail(), userRequest.getProvider_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found. ====>" + userRequest));

        if (user.getPassword().equals(userRequest.getPassword())) {
            return user;
        } else {
            throw new IllegalArgumentException("Password not match.");
        }
    }

    public User readUser(UserDeleteDto userRequest) {
        return userRepository.findByEmailAndProviderId(userRequest.getEmail(), userRequest.getProvider_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found. =>" + userRequest.getEmail()));
    }

    @Transactional
    public void updateUser(String email, UserRequest userRequest) {
        User user = userRepository.findByEmailAndProviderId(email, userRequest.getProvider_id()).orElseThrow(() -> new IllegalArgumentException("User not found. ====>" + email));
        String encode = bCryptPasswordEncoder.encode(userRequest.getPassword());
        user.update(encode, userRequest.getUserName());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String email, String provider_id) {
        Long l = userRepository.deleteByEmailAndProviderId(email, provider_id);
        if (l == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    // 임시 비밀번호 설정
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();

    // 임시 비밀번호 생성
    private static String generateTemporaryPassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }



    @Transactional
    public String updatePassword(UserPwdDto userPwdDto) {
        int length = 10;
        String temporaryPassword = generateTemporaryPassword(length);
        System.out.println("새 비밀번호: "+ temporaryPassword);

        // 사용자를 조회
        User user = userRepository.findByEmailAndUserName(userPwdDto.getEmail(), userPwdDto.getUserName()).orElse(null);

        // 사용자가 존재하지 않는 경우 예외 처리
        if (user == null) {
            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
        }

        // 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(temporaryPassword);

        // 업데이트
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        return temporaryPassword;
    }

}