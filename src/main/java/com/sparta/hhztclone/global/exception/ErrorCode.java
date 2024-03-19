package com.sparta.hhztclone.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND_EMAIL("존재하지 않는 이메일 입니다."),
    ;

    private final String msg;

    ErrorCode(String msg) {
        this.msg = msg;
    }
}
