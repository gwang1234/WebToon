package org.example.webtoonepics.community.exception;

import org.example.webtoonepics.community.dto.base.DefaultRes;

public class CustomException extends RuntimeException {
    private final DefaultRes defaultRes;

    public CustomException(DefaultRes defaultRes) {
        this.defaultRes = defaultRes;
    }

    public DefaultRes getDefaultRes() {
        return defaultRes;
    }
}

