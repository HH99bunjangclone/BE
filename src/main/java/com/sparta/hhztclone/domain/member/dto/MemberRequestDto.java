package com.sparta.hhztclone.domain.member.dto;

import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.domain.member.entity.type.AuthorityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MemberRequestDto {

    public record SignupMemberRequestDto(
            @Schema(description = "이메일", example = "test@email.com")
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "이메일 형식이 아닙니다.")
            String email,

            @Schema(description = "비밀번호", example = "Test123!")
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password,

            @NotBlank(message = "닉네임을 입력해주세요.")
            String nickname
    ) {
        public Member toEntity(String encodedPassword) {
            return Member.builder()
                    .email(email)
                    .password(encodedPassword)
                    .nickname(nickname)
                    .authority(AuthorityType.USER)
                    .build();
        }
    }

    public record LoginRequestDto(
            @Schema(description = "이메일", example = "test@email.com")
            @NotBlank(message = "이메일을 입력해주세요.")
            @Email(message = "이메일 형식이 아닙니다.")
            String email, // 인영님하고 request 이야기해보기

            @Schema(description = "비밀번호", example = "Test123!")
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password
    ) {

    }

    public record EditMemberRequestDto(
            @Schema(description = "비밀번호", example = "Test123!")
            @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
            String password
    ) {

    }
}
