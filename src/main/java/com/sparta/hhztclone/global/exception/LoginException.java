package com.sparta.hhztclone.global.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {

    public LoginException(String msg) {
        super(msg);
    }
}
