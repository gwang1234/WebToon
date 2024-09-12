package org.example.webtoonepics.dto;

import lombok.Data;

@Data
public class JwtDto {
    private String email;
    private String password;
    private String userName;
}
