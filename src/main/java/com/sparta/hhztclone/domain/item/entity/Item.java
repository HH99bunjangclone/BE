package com.sparta.hhztclone.domain.item.entity;

import com.sparta.hhztclone.domain.item.entity.Category.CategoryType;
import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    private List<String> imageUrl;

    @Builder
    public Item(Member member, String title, String contents, CategoryType category, int price, List<String> imageUrl) {
        this.member = member;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public void update(String title, String contents, Integer price, List<String> imageUrl) {
        if (title != null) {
            this.title = title;
        }
        if (contents != null) {
            this.contents = contents;
        }
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void uploadImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
