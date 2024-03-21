package com.sparta.hhztclone.domain.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.hhztclone.domain.item.entity.Item;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ItemResponseDto {


    @Getter
    public static class CreateItemResponseDto{
        private Long id;
        private String email;
        private String title;
        private String contents;
        private Integer price;
        private List<String> imageUrl;
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedAt;

        public CreateItemResponseDto(Item item) {
            this.id = item.getId();
            this.email = item.getMember().getEmail();
            this.title = item.getTitle();
            this.contents = item.getContents();
            this.price = item.getPrice();
            this.imageUrl = item.getImageUrl();
            this.createdAt = LocalDateTime.now();
            this.modifiedAt = LocalDateTime.now();
        }
    }

    @Getter
    public static class EditItemResponseDto{
        private Long id;
        private String email;
        private String title;
        private String contents;
        private Integer price;
        private List<String> imageUrl;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedAt;

        public EditItemResponseDto(Item item) {
            this.id = item.getId();
            this.email = item.getMember().getEmail();
            this.title = item.getTitle();
            this.contents = item.getContents();
            this.price = item.getPrice();
            this.imageUrl = item.getImageUrl();
            this.createdAt = item.getCreatedAt();
            this.modifiedAt = LocalDateTime.now();
        }
    }


    @Getter
    public static class GetItemResponseDto {
        private Long id;
        private String email;
        private String title;
        private String contents;
        private Integer price;
        private List<String> imageUrl;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime updatedAt;

        public GetItemResponseDto(Item item) {
            this.id = item.getId();
            this.email = item.getMember().getEmail();
            this.title = item.getTitle();
            this.contents = item.getContents();
            this.price = item.getPrice();
            this.imageUrl = item.getImageUrl();
            this.createdAt = item.getCreatedAt();
            this.updatedAt = item.getModifiedAt();
        }
    }

    @Getter
    public static class SearchItemResponseDto{
        private List<GetItemResponseDto> items;

        public SearchItemResponseDto(List<GetItemResponseDto> items) {
            this.items = items;
        }
    }
}
