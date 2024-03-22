package com.sparta.hhztclone.domain.image.entity;

import com.sparta.hhztclone.domain.item.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    private String originName;//본 이름
    private String storeName;// 이미지파일에 저장될 이름
    private String accessUrl;//내부 이미지에 접근할 수 있는 Url

    @Builder
    public Image(String originName, String savedImage, String accessUrl, Item item) {
        this.originName = originName;
        this.storeName = savedImage;
        this.accessUrl = accessUrl;
        this.item = item;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

}
