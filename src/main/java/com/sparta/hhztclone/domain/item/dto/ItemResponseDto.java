package com.sparta.hhztclone.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.hhztclone.domain.image.entity.Image;
import com.sparta.hhztclone.domain.item.entity.Item;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ItemResponseDto {


    @Getter
    public static class CreateItemResponseDto{
        private final Long id;
        private final String email;
        private final String title;
        private final String contents;
        private final Integer price;
        private final List<String> imageUrl;
        private final LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime modifiedAt;

        public CreateItemResponseDto(Item item, List<String> imageUrl) {
            this.id = item.getId();
            this.email = item.getMember().getEmail();
            this.title = item.getTitle();
            this.contents = item.getContents();
            this.price = item.getPrice();
            this.imageUrl = imageUrl;
            this.createdAt = LocalDateTime.now();
            this.modifiedAt = LocalDateTime.now();
        }
    }

    @Getter
    public static class EditItemResponseDto{
        private final Long id;
        private final String email;
        private final String title;
        private final String contents;
        private final Integer price;
        private final List<String> imageUrl;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime modifiedAt;

        public EditItemResponseDto(Item item, List<String> imageUrl) {
            this.id = item.getId();
            this.email = item.getMember().getEmail();
            this.title = item.getTitle();
            this.contents = item.getContents();
            this.price = item.getPrice();
            this.imageUrl = imageUrl;
            this.createdAt = item.getCreatedAt();
            this.modifiedAt = LocalDateTime.now();
        }
    }


    @Getter
    public static class GetItemResponseDto {
        private final Long id;
        private final String email;
        private final String title;
        private final String contents;
        private final Integer price;
        private final List<String> imageUrl;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime updatedAt;

        public GetItemResponseDto(Item item, List<String> image) {
            this.id = item.getId();
            this.email = item.getMember().getEmail();
            this.title = item.getTitle();
            this.contents = item.getContents();
            this.price = item.getPrice();
            this.imageUrl = image;
            this.createdAt = item.getCreatedAt();
            this.updatedAt = item.getModifiedAt();
        }
    }

    @Getter
    public static class SearchItemResponseDto{
        private final List<GetItemResponseDto> items;

        public SearchItemResponseDto(List<GetItemResponseDto> items) {
            this.items = items;
        }
    }
}
