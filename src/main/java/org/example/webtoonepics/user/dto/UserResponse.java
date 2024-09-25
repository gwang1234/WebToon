package org.example.webtoonepics.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.user.entity.User;

@Getter
@NoArgsConstructor
public class UserResponse {

    private String email;
    private String username;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.username = user.getUserName();
    }
}
