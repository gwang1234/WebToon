package org.example.webtoonepics.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.webtoonepics.user.entity.User;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String username;
    private String providerId;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUserName();
        this.providerId = user.getProviderId();
    }
}
