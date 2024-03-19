package com.sparta.hhztclone.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {

    public RestApiException(String msg) {
        super(msg);
    }
}