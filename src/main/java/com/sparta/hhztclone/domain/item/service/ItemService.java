package com.sparta.hhztclone.domain.item.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.hhztclone.domain.image.dto.ImageSaveDto;
import com.sparta.hhztclone.domain.image.entity.Image;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto;
import com.sparta.hhztclone.domain.item.entity.Item;
import com.sparta.hhztclone.domain.item.repository.ItemRepository;
import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {


    private static String bucketName = "aws-bucket-spt-test";
    private final AmazonS3Client amazonS3Client;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public ItemResponseDto.CreateItemResponseDto createItem(String email, ItemRequestDto.CreateItemRequestDto requestDto, ImageSaveDto file) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 이메일입니다."));
        Item savedItem = itemRepository.save(requestDto.toEntity(member));
        saveImages(file);
        return new ItemResponseDto.CreateItemResponseDto(savedItem);
    }

    public ItemResponseDto.GetItemResponseDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 아이템입니다."));
        return new ItemResponseDto.GetItemResponseDto(item);
    }

    @Transactional
    public ItemResponseDto.EditItemResponseDto editItem(Long itemId, String email, ItemRequestDto.EditItemRequestDto requestDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 이메일입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 아이템입니다."));
        if (item.getMember() != member) {
            throw new IllegalArgumentException("아이템 생성자와 일치하지 않습니다.");
        }
        item.update(requestDto.getTitle(),requestDto.getContents(), requestDto.getPrice());
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


    @Transactional
    public List<String> saveImages(ImageSaveDto saveDto) {
        List<String> resultList = new ArrayList<>();

        for (MultipartFile multipartFile : saveDto.getImages()) {
            String value = saveImage(multipartFile);
            resultList.add(value);
        }
        return resultList;
    }

    @Transactional
    public String saveImage(MultipartFile multipartFile) {
        String originalName = multipartFile.getOriginalFilename();
        Image image = new Image(originalName);
        String filename = image.getStoreName();

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);
            String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();

            image.setAccessUrl(accessUrl);
        } catch (IOException e) {

        }
        return image.getAccessUrl();
    }
}
