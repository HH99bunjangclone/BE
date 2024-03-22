package com.sparta.hhztclone.domain.image.dto;

import com.sparta.hhztclone.domain.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ImagesSaveDto {
    private List<MultipartFile> images = new ArrayList<>();

    @Getter
    public static class ItemImageResponseDto{
        private String originalName;
        private String savedImage;
        private String accessUrl;

        public ItemImageResponseDto(String originalName, String savedImage, String accessUrl) {
            this.originalName = originalName;
            this.savedImage = savedImage;
            this.accessUrl = accessUrl;
        }

        public Image toEntity() {
            return Image.builder()
                    .originName(this.originalName)
                    .savedImage(this.savedImage)
                    .accessUrl(this.accessUrl)
                    .build();
        }
    }

}
