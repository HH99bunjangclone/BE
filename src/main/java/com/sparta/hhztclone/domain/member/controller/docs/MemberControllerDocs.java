package com.sparta.hhztclone.domain.member.controller.docs;

import com.sparta.hhztclone.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.CheckMemberEmailResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.EmailAuthResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.EmailSendResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.SignupMemberResponseDto;
import com.sparta.hhztclone.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Member", description = "회원 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원가입", description = "회원가입 요청 API")
    ResponseDto<SignupMemberResponseDto> signup(@RequestBody @Valid SignupMemberRequestDto requestDto);

    @Operation(summary = "이메일 중복체크", description = "이메일 중복 확인 요청 API")
    ResponseDto<CheckMemberEmailResponseDto> emailCheck(@RequestParam String email);

    @Operation(summary = "이메일 인증 코드 전송", description = "이메일 인증 코드 전송 요청 API")
    ResponseDto<EmailSendResponseDto> emailSend(@RequestParam String email);

    @Operation(summary = "이메일 인증 체크", description = "이메일 인증 체크 요청 API")
    ResponseDto<EmailAuthResponseDto> emailAuthCheck(@RequestParam String email,
                                                     @RequestParam String emailCode);

}
