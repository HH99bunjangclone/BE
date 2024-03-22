package com.sparta.hhztclone.domain.image.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originName;//본 이름
    private String storeName;// 이미지파일에 저장될 이름
    private String accessUrl;//내부 이미지에 접근할 수 있는 Url

    public Image(String originName) {
        this.originName = originName;
        this.storeName = getFileName(originName);
        this.accessUrl = "";
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    //이미지에서 확장자 추출 메소드
    public String extractExtension(String originName) {
        int index = originName.lastIndexOf('.');
        return originName.substring(index, originName.length());
    }

    //파일 이름 저장을 위한 이름 변환 메서드
    public String getFileName(String originName) {
        return UUID.randomUUID() + "." + extractExtension(originName);
    }
}
