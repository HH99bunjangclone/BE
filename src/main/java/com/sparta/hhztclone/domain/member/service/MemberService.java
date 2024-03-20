package com.sparta.hhztclone.domain.member.service;

import com.sparta.hhztclone.domain.member.dto.MemberRequestDto.SignupMemberRequestDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.GetMemberResponseDto;
import com.sparta.hhztclone.domain.member.dto.MemberResponseDto.SignupMemberResponseDto;
import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.domain.member.repository.MemberRepository;
import com.sparta.hhztclone.global.exception.ErrorCode;
import com.sparta.hhztclone.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public SignupMemberResponseDto signup(SignupMemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.email())) {
            throw new RestApiException(ErrorCode.ALREADY_EXIST_EMAIL.getMsg());
        }
        String password = passwordEncoder.encode(requestDto.password());
        Member member = memberRepository.save(requestDto.toEntity(password));
        return new SignupMemberResponseDto(member);
    }

    // 회원 정보 조회
    public GetMemberResponseDto getMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new RestApiException(ErrorCode.NOT_FOUND_MEMBER.getMsg()));
        return new GetMemberResponseDto(member);
    }
}