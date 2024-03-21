package com.sparta.hhztclone.domain.item.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.hhztclone.domain.image.dto.ImageSaveDto;
import com.sparta.hhztclone.domain.image.entity.Image;
import com.sparta.hhztclone.domain.image.service.S3Service;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto;
import com.sparta.hhztclone.domain.item.entity.Item;
import com.sparta.hhztclone.domain.item.repository.ItemRepository;
import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {


    private final S3Service s3Service;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public ItemResponseDto.CreateItemResponseDto createItem(String email, ItemRequestDto.CreateItemRequestDto requestDto, MultipartFile[] multipartFiles) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 이메일입니다."));
        ImageSaveDto imageSaveDto = new ImageSaveDto();
        imageSaveDto.setImages(Arrays.asList(multipartFiles));
        List<String> images = s3Service.saveImages(imageSaveDto);
        Item savedItem = itemRepository.save(requestDto.toEntity(member,images));
        return new ItemResponseDto.CreateItemResponseDto(savedItem);
    }

    public ItemResponseDto.GetItemResponseDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 아이템입니다."));
        return new ItemResponseDto.GetItemResponseDto(item);
    }

    @Transactional
    public ItemResponseDto.EditItemResponseDto editItem(Long itemId, String email, ItemRequestDto.EditItemRequestDto requestDto, MultipartFile[] multipartFiles) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 이메일입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 아이템입니다."));
        if (item.getMember() != member) {
            throw new IllegalArgumentException("아이템 생성자와 일치하지 않습니다.");
        }
        List<String> existingImageUrls = item.getImageUrl();
        for (String imageUrl : existingImageUrls) {
            s3Service.deleteImage(imageUrl);
        }
        ImageSaveDto imageSaveDto = new ImageSaveDto();
        imageSaveDto.setImages(Arrays.asList(multipartFiles));
        List<String> newImages = s3Service.saveImages(imageSaveDto);

        item.update(requestDto.getTitle(),requestDto.getContents(), requestDto.getPrice(), newImages);
        return new ItemResponseDto.EditItemResponseDto(item);
    }

    @Transactional
    public void deleteItem(Long itemId, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 이메일입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 아이템입니다."));
        if (item.getMember() != member) {
            throw new IllegalArgumentException("아이템 생성자와 일치하지 않습니다.");
        }
        itemRepository.delete(item);
    }

}
