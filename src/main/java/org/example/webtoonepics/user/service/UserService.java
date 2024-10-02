package org.example.webtoonepics.user.service;

import lombok.RequiredArgsConstructor;
import org.example.webtoonepics.user.dto.UserRequest;
import org.example.webtoonepics.user.entity.User;
import org.example.webtoonepics.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User loginUser(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found. ====>" + userRequest));

        if (user.getPassword().equals(userRequest.getPassword())) {
            return user;
        } else {
            throw new IllegalArgumentException("Password not match.");
        }
    }

    public User createUser(UserRequest userRequest) {
        return userRepository.save(userRequest.toEntity());
    }

    public User readUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found. =>" + id));
    }

    @Transactional
    public User updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found. ====>" + id));
        user.update(userRequest.getPassword(), userRequest.getUserName());
        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    
}