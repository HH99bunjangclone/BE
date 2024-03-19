package com.sparta.hhztclone.domain.item.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ItemResponseDto {


    @Getter
    public static class CreateItemResponseDto{
        private Long id;
        private String email;
        private String title;
        private String contents;
        private LocalDateTime createdAt;

        public CreateItemResponseDto(Long id, String email, String title, String contents, LocalDateTime createdAt) {
            this.id = id;
            this.email = email;
            this.title = title;
            this.contents = contents;
            this.createdAt = createdAt;
        }
    }

    @Getter
    public static class EditItemResponseDto{
        private Long id;
        private String email;
        private String title;
        private String contents;
        private LocalDateTime createdAt;

        public EditItemResponseDto(Long id, String email, String title, String contents, LocalDateTime createdAt) {
            this.id = id;
            this.email = email;
            this.title = title;
            this.contents = contents;
            this.createdAt = createdAt;
        }
    }


    @Getter
    public static class GetItemResponseDto {
        private Long id;
        private String email;
        private String title;
        private String contents;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public GetItemResponseDto(Long id, String email, String title, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.email = email;
            this.title = title;
            this.contents = contents;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }

    @Getter
    public static class SearchItemResponseDto{
        private Long id;
        private String email;
        private String title;
        private String contents;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public SearchItemResponseDto(Long id, String email, String title, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.email = email;
            this.title = title;
            this.contents = contents;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
