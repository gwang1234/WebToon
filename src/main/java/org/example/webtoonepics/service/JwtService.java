package org.example.webtoonepics.service;

import org.example.webtoonepics.dto.JwtDto;
import org.example.webtoonepics.entity.Role;
import org.example.webtoonepics.entity.User;
import org.example.webtoonepics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class JwtService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Boolean auth(JwtDto jwtDto) {

        String email = jwtDto.getEmail();
        String passwd = jwtDto.getPassword();
        String username = jwtDto.getUserName();

        if (passwd == null || username == null || email == null) {
            return false;
        }

        Boolean exist = userRepository.exist(username, email);
        if (exist) {
            return false;
        }

        User user = new User();

        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(passwd));
        user.setUserName(username);
        if (Objects.equals(email, "qoi11@naver.com")) {
            user.setRole(Role.ROLE_BOSS);
        } else {
            user.setRole(Role.ROLE_USER);
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return true;
    }

    public Boolean existEmail(String email) {
        Boolean byEmail = userRepository.existsByEmail(email);
        if (byEmail) {
            return true;
        }
        return false;
    }

    public Boolean existUsername(String userName) {
        Boolean exists = userRepository.existsByUserName(userName);
        if (exists) {
            return true;
        }
        return false;
    }

    public String findRoleByEmail(String email) {
        return String.valueOf(userRepository.findByEmail(email).getRole());
    }
}
