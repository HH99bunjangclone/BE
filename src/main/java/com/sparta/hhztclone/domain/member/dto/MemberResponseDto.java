package com.sparta.hhztclone.domain.member.dto;

import com.sparta.hhztclone.domain.member.entity.Member;

public class MemberResponseDto {

    public record SignupMemberResponseDto(
            String email
    ) {
        public SignupMemberResponseDto(Member member) {
            this(
                    member.getEmail()
            );
        }
    }

    public record LoginResponseDto(
            Long id
    ) {
        public LoginResponseDto(Member member) {
            this(
                    member.getId()
            );
        }
    }

    public record EditMemberResponseDto(
            Long id
    ) {
        public EditMemberResponseDto(Member member) {
            this(
                    member.getId()
            );
        }
    }

    public record CheckMemberEmailResponseDto(
            Boolean isExist
    ) {

    }

    public record GetMemberResponseDto(
            Long id,
            String email

    ) {
        public GetMemberResponseDto(Member member) {
            this(
                    member.getId(),
                    member.getEmail()
            );
        }
    }

    public record EmailSendResponseDto(String emailCode){}

    public record EmailAuthResponseDto(Boolean success){}
}
