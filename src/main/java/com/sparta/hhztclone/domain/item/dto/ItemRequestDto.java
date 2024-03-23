package com.sparta.hhztclone.domain.item.dto;

import com.sparta.hhztclone.domain.item.entity.Category.CategoryType;
import com.sparta.hhztclone.domain.item.entity.Item;
import com.sparta.hhztclone.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import static com.sparta.hhztclone.domain.item.valid.ItemValidationGroup.*;

public class ItemRequestDto {


    @Getter
    @Setter
    public static class CreateItemRequestDto{

        @Schema(description = "제목", example ="옷1")
        @NotBlank(message = "제목을 입력해 주세요.", groups = TitleBlankGroup.class)
        private String title;

        @Schema(description = "내용", example ="옷1")
        @NotBlank(message = "내용을 입력해 주세요", groups = ContentsBlankGroup.class)
        private String contents;

        @Schema(description = "가격", example = "1000")
        @PositiveOrZero(message = "가격을 입력해 주세요", groups = PriceRangeGroup.class)
        private Integer price;

        private CategoryType category;

        public Item toEntity(Member member) {
            return Item.builder()
                    .member(member)
                    .title(this.title)
                    .contents(this.contents)
                    .price(this.price)
                    .category(this.category)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class EditItemRequestDto{
        @Schema(description = "제목", example = "옷2")
        @NotBlank(message = "제목을 입력해 주세요.", groups = TitleBlankGroup.class)
        private String title;

        @Schema(description = "내용", example = "옷2")
        @NotBlank(message = "내용을 입력해 주세요", groups = ContentsBlankGroup.class)
        private String contents;

        @Schema(description = "가격", example = "1000")
        @PositiveOrZero(message = "가격을 입력해 주세요", groups = PriceRangeGroup.class)
        private Integer price;

        public EditItemRequestDto(String title, String contents, Integer price) {
            this.title = title;
            this.contents = contents;
            this.price = price;
        }
    }

}
