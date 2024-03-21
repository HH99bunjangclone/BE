package com.sparta.hhztclone.domain.item.dto;

import com.sparta.hhztclone.domain.item.entity.Item;
import com.sparta.hhztclone.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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

        public CreateItemResponseDto(Item item) {
            this.id = item.getId();
            this.email = item.getMember().getEmail();
            this.title = item.getTitle();
            this.contents = item.getContents();
            this.price = item.getPrice();
            this.imageUrl = item.getImageUrl();
            this.createdAt = LocalDateTime.now();
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
        private LocalDateTime createdAt;

        public EditItemResponseDto(Item item) {
            this.id = item.getId();
            this.email = item.getMember().getEmail();
            this.title = item.getTitle();
            this.contents = item.getContents();
            this.price = item.getPrice();
            this.imageUrl = item.getImageUrl();
            this.createdAt = item.getCreatedAt();
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
        private LocalDateTime createdAt;
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
        private Long id;
        private String email;
        private String title;
        private String contents;
        private Integer price;
        private String imageUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public SearchItemResponseDto(Long id, String email, String title, String contents, Integer price, String imageUrl,LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.email = email;
            this.title = title;
            this.contents = contents;
            this.price = price;
            this.imageUrl = imageUrl;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
