package com.sparta.hhztclone.domain.item.service;

import com.sparta.hhztclone.domain.image.dto.ImageSaveDto;
import com.sparta.hhztclone.domain.image.service.S3Service;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto.CreateItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto.EditItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto;
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

    @Transactional
    public ItemResponseDto.CreateItemResponseDto createItem(String email, CreateItemRequestDto requestDto, MultipartFile[] multipartFiles) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없는 이메일입니다."));
        ImageSaveDto imageSaveDto = new ImageSaveDto();
        imageSaveDto.setImages(Arrays.asList(multipartFiles));
        List<String> images = s3Service.saveImages(imageSaveDto);
        Item savedItem = itemRepository.save(requestDto.toEntity(member,images));
        return new ItemResponseDto.CreateItemResponseDto(savedItem);
    }

    public GetItemResponseDto getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 아이템입니다."));
        return new GetItemResponseDto(item);
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
        List<String> existingImageUrls = item.getImageUrl();
        for (String imageUrl : existingImageUrls) {
            s3Service.deleteImage(imageUrl);
        }
        ImageSaveDto imageSaveDto = new ImageSaveDto();
        imageSaveDto.setImages(Arrays.asList(multipartFiles));
        List<String> newImages = s3Service.saveImages(imageSaveDto);

        item.update(requestDto.getTitle(),requestDto.getContents(), requestDto.getPrice(), newImages);
        return new EditItemResponseDto(item);
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

    public SearchItemResponseDto getItems() {
        List<GetItemResponseDto> items = itemRepository.findAll().stream()
                .map(GetItemResponseDto::new).collect(Collectors.toList());
        return new SearchItemResponseDto(items);
    }

    public SearchItemResponseDto searchItems(String keyword) {
        List<GetItemResponseDto> searchedItems = itemrepositoryImpl.searchItems(keyword).stream()
                .map(GetItemResponseDto::new).collect(Collectors.toList());
        return new SearchItemResponseDto(searchedItems);
    }

    public SearchItemResponseDto searchItemsByCategory(CategoryType category) {
        List<GetItemResponseDto> items = itemRepository.findByCategory(category).stream()
                .map(GetItemResponseDto::new).collect(Collectors.toList());
        return new SearchItemResponseDto(items);
    }
}
