package com.sparta.hhztclone.domain.image.repository;

import com.sparta.hhztclone.domain.image.entity.Image;
import com.sparta.hhztclone.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByItem(Item item);
}
