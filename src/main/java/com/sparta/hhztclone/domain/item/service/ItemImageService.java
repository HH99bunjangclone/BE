package com.sparta.hhztclone.domain.item.service;

import com.sparta.hhztclone.domain.image.dto.ImagesSaveDto;
import com.sparta.hhztclone.domain.image.dto.ImagesSaveDto.ItemImageResponseDto;
import com.sparta.hhztclone.domain.image.entity.Image;
import com.sparta.hhztclone.domain.image.repository.ImageRepository;
import com.sparta.hhztclone.domain.image.service.S3Service;
import com.sparta.hhztclone.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemImageService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    @Transactional
    public ItemImageResponseDto createItemImage(MultipartFile file, Item item) {
        ItemImageResponseDto itemImageResponseDto = s3Service.saveImage(file);
        Image save = imageRepository.save(new Image(
                itemImageResponseDto.getOriginalName(),
                itemImageResponseDto.getSavedImage(),
                itemImageResponseDto.getAccessUrl(),
                item));
        return itemImageResponseDto;
    }
}
