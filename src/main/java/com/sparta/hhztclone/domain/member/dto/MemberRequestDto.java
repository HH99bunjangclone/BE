package com.sparta.hhztclone.domain.member.dto;

import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.domain.member.entity.type.AuthorityType;
import com.sparta.hhztclone.domain.member.valid.MemberValidationGroup.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MemberRequestDto {

    public record SignupMemberRequestDto(
            @Schema(description = "이메일", example = "test@email.com")
            @NotBlank(message = "이메일을 입력해주세요.", groups = EmailBlankGroup.class)
            @Email(message = "이메일 형식이 아닙니다.", groups = EmailGroup.class)
            String email,

            @Schema(description = "비밀번호", example = "Test123!")
            @NotBlank(message = "비밀번호를 입력해주세요.", groups = PasswordBlankGroup.class)
            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_~]).{8,15}$",
                    message = "비밀번호는 영어 대소문자, 숫자, 특수문자를 포함한 8~15자리 입니다.", groups = PasswordPatternGroup.class)
            String password,

            @NotBlank(message = "닉네임을 입력해주세요.", groups = NicknameBlankGroup.class)
            @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리를 입력 해주세요.", groups = NicknamePatternGroup.class)
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
            String email,

            @Schema(description = "비밀번호", example = "Test123!")
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password
    ) {

    }

}
