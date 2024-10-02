package org.example.webtoonepics.user.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpServerErrorException;

public class KakaoTokenRequestException extends RuntimeException {
    public KakaoTokenRequestException(String message) {
        super(message);
    }
}
