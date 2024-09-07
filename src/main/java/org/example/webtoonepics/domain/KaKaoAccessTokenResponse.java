package org.example.webtoonepics.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KaKaoAccessTokenResponse {
    
    @JsonProperty("access_token")
    private String accessToken;
}