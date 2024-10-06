package org.example.webtoonepics.user.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.community.exception.ResponseMessage;
import org.example.webtoonepics.user.dto.UserDeleteDto;
import org.example.webtoonepics.user.dto.UserRequest;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        return userRepository.findByEmailAndProviderId(userRequest.getEmail(), userRequest.getProvider_id()).orElseThrow(() -> new IllegalArgumentException("User not found. =>" + userRequest.getEmail()));
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

    
}