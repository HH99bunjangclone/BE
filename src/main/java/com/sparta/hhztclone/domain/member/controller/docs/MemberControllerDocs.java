package com.sparta.hhztclone.domain.member.controller.docs;

import com.sparta.hhztclone.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.EmailAuthResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.EmailSendResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.GetMemberResponseDto;
import com.sparta.hhztclone.global.dto.ResponseDto;
import com.sparta.hhztclone.domain.member.valid.MemberValidationGroup.EmailGroup;
import com.sparta.hhztclone.domain.member.valid.MemberValidationGroup.NicknamePatternGroup;
import com.sparta.hhztclone.domain.member.valid.MemberValidationGroup.NotBlankGroup;
import com.sparta.hhztclone.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Member", description = "회원 API")
public interface MemberControllerDocs {

    @Operation(summary = "회원가입", description = "회원가입 요청 API")
    ResponseDto signup(@RequestBody @Valid SignupMemberRequestDto requestDto);

    @Operation(summary = "이메일 중복체크", description = "이메일 중복 확인 요청 API")
    ResponseEntity<ResponseDto> emailCheck(
            @RequestParam
            @NotBlank(message = "이메일을 입력해주세요.", groups = NotBlankGroup.class)
            @Email(message = "잘못된 이메일 형식입니다.", groups = EmailGroup.class)
            String email);

    @Operation(summary = "이메일 인증 코드 전송", description = "이메일 인증 코드 전송 요청 API")
    ResponseDto<EmailSendResponseDto> emailSend(
            @RequestParam
            @NotBlank(message = "이메일을 입력해주세요.", groups = NotBlankGroup.class)
            @Email(message = "잘못된 이메일 형식입니다.", groups = EmailGroup.class)
            String email
    );

    @Operation(summary = "이메일 인증 체크", description = "이메일 인증 체크 요청 API")
    ResponseDto<EmailAuthResponseDto> emailAuthCheck(@RequestParam("email") String email,
                                                     @RequestParam("emailCode")
                                                     @NotBlank(message = "인증 코드를 입력해주세요.", groups = NotBlankGroup.class)
                                                     String emailCode);

    @Operation(summary = "닉네임 중복체크", description = "닉네임 중복 확인 요청 API")
    ResponseEntity<ResponseDto> nicknameCheck(
            @RequestParam
            @NotBlank(message = "닉네임을 입력해주세요.", groups = NotBlankGroup.class)
            @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리를 입력 해주세요.", groups = NicknamePatternGroup.class)
            String nickname);

    // 회원 정보 조회
    @Operation(summary = "회원 정보 조회 API", description = "마이페이지 회원 정보 조회 요청 API")
    ResponseDto<GetMemberResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails);

}
