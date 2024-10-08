package org.example.webtoonepics.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDeleteDto {

    private String email;
    private String provider_id;
}
