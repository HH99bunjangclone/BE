package com.sparta.hhztclone.domain.item.valid;

import jakarta.validation.GroupSequence;

import static com.sparta.hhztclone.domain.item.valid.ItemValidationGroup.*;

@GroupSequence({
        NotBlankGroup.class,
        TitleBlankGroup.class,
        ContentsBlankGroup.class,
        PriceRangeGroup.class
})
public interface ItemValidationSequence {
}
