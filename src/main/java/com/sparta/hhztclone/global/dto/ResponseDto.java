package com.sparta.hhztclone.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto<T>(
        boolean status,
        String msg,
        T data
) {

    public static <T> ResponseDto<T> success(String msg, T data) {
        return new ResponseDto<>(true, msg, data);
    }

    public static <T> ResponseDto<T> fail(String msg) {
        return new ResponseDto<>(false, msg, null);
    }

    public static <T> ResponseDto<T> fail(String msg, T data) {
        return new ResponseDto<>(false, msg, data);
    }
}
