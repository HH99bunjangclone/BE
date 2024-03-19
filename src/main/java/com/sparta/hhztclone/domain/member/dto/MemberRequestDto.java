package com.sparta.hhztclone.domain.member.dto;

import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.domain.member.entity.type.AuthorityType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MemberRequestDto {

    public record SignupMemberRequestDto(
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "이메일 형식이 아닙니다.")
            String email,

            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password
    ) {
        public Member toEntity(String encodedPassword) {
            return Member.builder()
                    .email(email)
                    .password(encodedPassword)
                    .authority(AuthorityType.USER)
                    .build();
        }
    }

    public record SigninMemberRequestDto(
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "이메일 형식이 아닙니다.")
            String username,

            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password
    ) {

    }

    public record EditMemberRequestDto(
            @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
            String password
    ) {

    }
}
