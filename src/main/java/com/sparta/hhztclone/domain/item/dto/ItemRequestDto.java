package com.sparta.hhztclone.domain.item.dto;

import com.sparta.hhztclone.domain.item.entity.Item;
import com.sparta.hhztclone.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ItemRequestDto {


    @Getter
    public static class CreateItemRequestDto{

        @Schema(description = "제목", example = "옷1")
        @NotBlank(message = "제목을 입력해 주세요")
        private String title;

        @Schema(description = "내용", example = "옷1")
        @NotBlank(message = "내용을 입력해 주세요.")
        private String contents;

        public Item toEntity(Member member) {
            return Item.builder()
                    .member(member)
                    .title(this.title)
                    .contents(this.contents)
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

        public EditItemRequestDto(String title, String contents) {
            this.title = title;
            this.contents = contents;
        }
    }

}
