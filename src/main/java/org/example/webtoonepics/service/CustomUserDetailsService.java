package org.example.webtoonepics.service;

import java.util.Optional;
import org.example.webtoonepics.dto.CustomUserDetails;
import org.example.webtoonepics.entity.User;
import org.example.webtoonepics.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userData = userRepository.findByEmail(email);
        if (userData.isPresent()) {
            return new CustomUserDetails(userData.get());
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
