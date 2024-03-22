package com.sparta.hhztclone.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.hhztclone.domain.member.entity.Member;

import java.time.LocalDateTime;

public class MemberResponseDto {

    public record CheckMemberEmailResponseDto(Boolean isExist) {}

    public record CheckMemberNicknameResponseDto(Boolean isExist) {}

    public record GetMemberResponseDto(
            Long id,
            String email,
            String nickname,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime createdAt
    ) {
        public GetMemberResponseDto(Member member) {
            this(
                    member.getId(),
                    member.getEmail(),
                    member.getNickname(),
                    member.getCreatedAt()
            );
        }
    }

    public record EmailSendResponseDto(String emailCode){}

    public record EmailAuthResponseDto(Boolean success){}
}
