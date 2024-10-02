package org.example.webtoonepics.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.entity.Role;
import org.example.webtoonepics.user.entity.User;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String email;
    private String password;
    private String userName;
    
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .role(Role.ROLE_USER)
                .build();
    }
}
