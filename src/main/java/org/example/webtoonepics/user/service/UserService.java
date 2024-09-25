package org.example.webtoonepics.user.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found. =>" + email));
    }
}
