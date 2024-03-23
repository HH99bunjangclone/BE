package com.sparta.hhztclone.domain.member.valid;

import com.sparta.hhztclone.domain.member.valid.MemberValidationGroup.*;
import jakarta.validation.GroupSequence;

@GroupSequence({
        NotBlankGroup.class,
        EmailBlankGroup.class,
        EmailGroup.class,
        PasswordBlankGroup.class,
        PasswordPatternGroup.class,
        NicknameBlankGroup.class,
        NicknamePatternGroup.class,
})
public interface MemberValidationSequence {
}
