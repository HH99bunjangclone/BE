package com.sparta.hhztclone.domain.item.service;

import com.sparta.hhztclone.domain.image.dto.ImagesSaveDto;
import com.sparta.hhztclone.domain.image.dto.ImagesSaveDto.ItemImageResponseDto;
import com.sparta.hhztclone.domain.image.entity.Image;
import com.sparta.hhztclone.domain.image.repository.ImageRepository;
import com.sparta.hhztclone.domain.image.service.S3Service;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto.CreateItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto.EditItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.CreateItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.EditItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.GetItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.SearchItemResponseDto;
import com.sparta.hhztclone.domain.item.entity.Category.CategoryType;
import com.sparta.hhztclone.domain.item.entity.Item;
import com.sparta.hhztclone.domain.item.repository.ItemRepository;
import com.sparta.hhztclone.domain.item.repository.ItemRepositoryImpl;
import com.sparta.hhztclone.domain.member.entity.Member;
import com.sparta.hhztclone.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final S3Service s3Service;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepositoryImpl itemrepositoryImpl;
    private final ImageRepository imageRepository;
    private final ItemImageService itemImageService;

    @Transactional
    public CreateItemResponseDto createItem(String email, CreateItemRequestDto requestDto, MultipartFile[] multipartFiles) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 이메일입니다."));

        Item item = requestDto.toEntity(member);
        List<String> imageUrl = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            ItemImageResponseDto itemImage = itemImageService.createItemImage(multipartFile, item);
            String accessUrl = itemImage.getAccessUrl();
            imageUrl.add(accessUrl);
        }
        Item itemWithImage = itemRepository.save(item);


        return new CreateItemResponseDto(itemWithImage, imageUrl);
    }

    public GetItemResponseDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 아이템입니다."));
        List<String> byItem = imageRepository.findByItem(item).stream().map(Image::getAccessUrl).collect(Collectors.toList());
        return new GetItemResponseDto(item, byItem);
    }

    @Transactional
    public EditItemResponseDto editItem(Long itemId, String email, EditItemRequestDto requestDto, MultipartFile[] multipartFiles) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 이메일입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 아이템입니다."));
        if (item.getMember() != member) {
            throw new IllegalArgumentException("아이템 생성자와 일치하지 않습니다.");
        }
        List<Image> imageList = imageRepository.findByItem(item);
        for (Image imageUrl : imageList) {
            s3Service.deleteImage(imageUrl.getStoreName());
            imageRepository.delete(imageUrl);
        }
        ImagesSaveDto imageSaveDto = new ImagesSaveDto();
        imageSaveDto.setImages(Arrays.asList(multipartFiles));

        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            ItemImageResponseDto itemImage = itemImageService.createItemImage(multipartFile, item);
            String accessUrl = itemImage.getAccessUrl();
            imageUrls.add(accessUrl);
        }
        item.update(requestDto.getTitle(), requestDto.getContents(), requestDto.getPrice());
        return new EditItemResponseDto(item, imageUrls);
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
        List<Image> byItem = imageRepository.findByItem(item);
        for (Image image : byItem) {
            imageRepository.delete(image);
            s3Service.deleteImage(image.getAccessUrl());
        }
        itemRepository.delete(item);
    }

    public SearchItemResponseDto getItems() {
        List<Item> allItem = itemRepository.findAll();

        List<GetItemResponseDto> items = new ArrayList<>();
        for (Item item : allItem) {
            List<Image> images = imageRepository.findByItem(item);

            List<String> imageUrls = new ArrayList<>();
            for (Image image : images) {
                imageUrls.add(image.getAccessUrl());
            }

            GetItemResponseDto getItemResponseDto = new GetItemResponseDto(item, imageUrls);
            items.add(getItemResponseDto);
        }
        return new SearchItemResponseDto(items);
    }

    public SearchItemResponseDto searchItems(String keyword) {
        List<Item> allItem = itemrepositoryImpl.searchItems(keyword);

        List<GetItemResponseDto> items = new ArrayList<>();
        for (Item item : allItem) {
            List<Image> images = imageRepository.findByItem(item);

            List<String> imageUrls = new ArrayList<>();
            for (Image image : images) {
                imageUrls.add(image.getAccessUrl());
            }
            GetItemResponseDto getItemResponseDto = new GetItemResponseDto(item, imageUrls);
            items.add(getItemResponseDto);
        }

        return new SearchItemResponseDto(items);
    }

    public SearchItemResponseDto searchItemsByCategory(CategoryType category) {
        List<Item> allItem = itemRepository.findByCategory(category);

        List<GetItemResponseDto> items = new ArrayList<>();
        for (Item item : allItem) {
            List<Image> images = imageRepository.findByItem(item);

            List<String> imageUrls = new ArrayList<>();
            for (Image image : images) {
                imageUrls.add(image.getAccessUrl());
            }
            GetItemResponseDto getItemResponseDto = new GetItemResponseDto(item, imageUrls);
            items.add(getItemResponseDto);
        }
        return new SearchItemResponseDto(items);
    }
}
