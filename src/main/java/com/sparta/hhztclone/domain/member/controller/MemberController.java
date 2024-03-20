package com.sparta.hhztclone.domain.member.controller;

import com.sparta.hhztclone.domain.member.controller.docs.MemberControllerDocs;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.CheckMemberEmailResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.EmailAuthResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.EmailSendResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.SignupMemberResponseDto;
import com.sparta.hhztclone.domain.member.service.EmailService;
import com.sparta.hhztclone.domain.member.service.MemberService;
import com.sparta.hhztclone.global.dto.ResponseDto;
import com.sparta.hhztclone.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sparta.hhztclone.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;
import static com.sparta.hhztclone.domain.member.dto.MemberResponseDto.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class MemberController implements MemberControllerDocs {

    private final EmailService emailService;
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseDto<SignupMemberResponseDto> signup(@RequestBody @Valid SignupMemberRequestDto requestDto) {
        SignupMemberResponseDto responseDto = memberService.signup(requestDto);
        return ResponseDto.success("회원가입 성공", responseDto);
    }
    
    // 이메일 중복 체크 요청
    @GetMapping("/email/check")
    public ResponseDto<CheckMemberEmailResponseDto> emailCheck(@RequestParam String email) {
        boolean isExist = emailService.emailCheck(email);
        return ResponseDto.success("이메일 중복체크 성공", new CheckMemberEmailResponseDto(isExist));
    }
    
    // 이메일 인증 코드 발송
    @PostMapping("/email-code")
    public ResponseDto<EmailSendResponseDto> emailSend(@RequestParam String email) {
        String emailCode = emailService.emailSend(email);
        return ResponseDto.success("이메일 인증 코드 발송 성공", new EmailSendResponseDto(emailCode));
    }
    
    // 이메일 인증 코드 체크
    @GetMapping("/email-auth")
    public ResponseDto<EmailAuthResponseDto> emailAuthCheck(@RequestParam("email") String email,
                                                            @RequestParam("emailCode") String emailCode) {
        boolean success = emailService.emailAuthCheck(email, emailCode);
        return ResponseDto.success("이메일 인증 성공", new EmailAuthResponseDto(success));
    }

    // 회원 정보 조회
    @GetMapping("/{userId}")
    public ResponseDto<GetMemberResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        GetMemberResponseDto responseDto = memberService.getMember(userDetails.getUsername());
        return ResponseDto.success("회원 정보 조회 성공", responseDto);
    }
}
