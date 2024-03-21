package com.sparta.hhztclone.domain.item.dto;

import com.sparta.hhztclone.domain.item.entity.Category.CategoryType;
import com.sparta.hhztclone.domain.item.entity.Item;
import com.sparta.hhztclone.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.util.List;

public class ItemRequestDto {


    @Getter
    public static class CreateItemRequestDto{

        @Schema(description = "제목", example ="옷1")
        private String title;

        @Schema(description = "내용", example ="옷1")
        private String contents;

        @Schema(description = "가격", example = "1000")
        @PositiveOrZero(message = "가격을 입력해 주세요")
        private int price;

        private CategoryType category;

        private List<String> files;

        public Item toEntity(Member member, List<String> images) {
            return Item.builder()
                    .member(member)
                    .title(this.title)
                    .contents(this.contents)
                    .price(this.price)
                    .category(this.category)
                    .imageUrl(images)
                    .build();
        }
    }

    @Getter
    public static class EditItemRequestDto{
        @Schema(description = "제목", example = "옷2")
        @NotBlank(message = "제목을 입력해 주세요")
        private String title;

        @Schema(description = "내용", example = "옷2")
        @NotBlank(message = "내용을 입력해 주세요.")
        private String contents;

        @Schema(description = "가격", example = "1000")
        @PositiveOrZero(message = "가격을 입력해 주세요")
        private int price;

        private List<String> imgList;

        public EditItemRequestDto(String title, String contents, Integer price, List<String> images) {
            this.title = title;
            this.contents = contents;
            this.price = price;
            this.imgList = images;
        }
    }

}
