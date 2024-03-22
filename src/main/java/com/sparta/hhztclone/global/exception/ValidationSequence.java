package com.sparta.hhztclone.global.exception;

import com.sparta.hhztclone.global.exception.ValidationGroup.*;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroup.class, PatternGroup.class, EmailGroup.class})
public interface ValidationSequence {
}
