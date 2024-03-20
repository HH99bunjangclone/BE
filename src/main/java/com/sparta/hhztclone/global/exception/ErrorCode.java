package com.sparta.hhztclone.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND_EMAIL("존재하지 않는 이메일 입니다."),
    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다."),
    NOT_FOUND_MEMBER("존재하지 않는 회원입니다."),
    ;

    private final String msg;

    ErrorCode(String msg) {
        this.msg = msg;
    }
}
