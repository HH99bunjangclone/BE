package com.sparta.hhztclone.domain.member.controller.docs;

import com.sparta.hhztclone.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.*;
import com.sparta.hhztclone.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Member", description = "회원 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원가입", description = "회원가입 요청 API")
    ResponseDto<SignupMemberResponseDto> signup(@RequestBody @Valid SignupMemberRequestDto requestDto);

    @Operation(summary = "이메일 중복체크", description = "이메일 중복 확인 요청 API")
    ResponseDto<CheckMemberEmailResponseDto> emailCheck(
            @RequestParam
            @Email(message = "이메일 형식이 아닙니다.")
            @NotBlank(message = "이메일을 입력해주세요.")
            String email);

    @Operation(summary = "이메일 인증 코드 전송", description = "이메일 인증 코드 전송 요청 API")
    ResponseDto<EmailSendResponseDto> emailSend(@RequestParam String email);

    @Operation(summary = "이메일 인증 체크", description = "이메일 인증 체크 요청 API")
    ResponseDto<EmailAuthResponseDto> emailAuthCheck(@RequestParam String email,
                                                     @RequestParam String emailCode);

    @Operation(summary = "닉네임 중복체크", description = "닉네임 중복 확인 요청 API")
    ResponseDto<CheckMemberNicknameResponseDto> nicknameCheck(
            @RequestParam
            @NotBlank(message = "닉네임을 입력해주세요.")
            @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "특수문자를 제외한 2~10자리")
            String nickname);

}
