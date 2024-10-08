package org.example.webtoonepics.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.jwt_login.entity.Role;
import org.example.webtoonepics.user.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String email;
    private String password;
    private String userName;
    private String provider_id;
//    @JsonIgnore
//    private BCryptPasswordEncoder bcryptPasswordEncoder;
    
//    public User toEntity() {
//        return User.builder()
//                .email(email)
//                .password(bCryptPasswordEncoder.encode(password))
//                .userName(userName)
//                .role(Role.ROLE_USER)
//                .build();
//    }
}
