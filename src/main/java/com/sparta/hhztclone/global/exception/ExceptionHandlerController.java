package com.sparta.hhztclone.global.exception;

import com.sparta.hhztclone.global.dto.ResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> invalidFieldRequest(MethodArgumentNotValidException e) {
        Map<String, String> data = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            data.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(ResponseDto.fail("유효성 검사 실패", data));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<List<String>>> checkFieldRequest(ConstraintViolationException e) {
        List<String> data = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();

        return ResponseEntity.badRequest().body(ResponseDto.fail("유효성 검사 실패", data));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> clientBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(ResponseDto.fail(e.getMessage()));
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ResponseDto> restApiBadRequest(RestApiException e) {
        return ResponseEntity.badRequest().body(ResponseDto.fail(e.getMessage()));
    }
}
