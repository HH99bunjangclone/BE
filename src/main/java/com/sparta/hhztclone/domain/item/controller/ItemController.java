package com.sparta.hhztclone.domain.item.controller;

import com.sparta.hhztclone.domain.item.controller.docs.ItemControllerDocs;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.CreateItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.GetItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.SearchItemResponseDto;
import com.sparta.hhztclone.domain.item.entity.Category.CategoryType;
import com.sparta.hhztclone.domain.item.service.ItemService;
import com.sparta.hhztclone.domain.item.valid.ItemValidationSequence;
import com.sparta.hhztclone.global.dto.ResponseDto;
import com.sparta.hhztclone.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/item")
@DynamicUpdate
@Slf4j
@Validated(ItemValidationSequence.class)
public class ItemController implements ItemControllerDocs {

    private final ItemService itemService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<CreateItemResponseDto> createItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart @Valid ItemRequestDto.CreateItemRequestDto requestDto,
            @RequestPart(value = "imgList", required = false) MultipartFile[] multipartFilesList
    ) {
        try {
            CreateItemResponseDto responseDto = itemService.createItem(userDetails.getUsername(), requestDto, multipartFilesList);
            return ResponseDto.success("아이템 생성 기능", responseDto);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("1장 이상의 이미지를 선택해 주세요.");
        }
    }

    @GetMapping
    public ResponseDto<SearchItemResponseDto> getItems() {
        SearchItemResponseDto responseDto = itemService.getItems();
        return ResponseDto.success("아이템 목록 조회", responseDto);
    }

    @GetMapping("/{itemId}")
    public ResponseDto<GetItemResponseDto> getItem(
            @PathVariable Long itemId
    ) {
        GetItemResponseDto responseDto = itemService.getItem(itemId);
        return ResponseDto.success("아이템 조회 기능", responseDto);
    }

    @GetMapping("/search")
    public ResponseDto<SearchItemResponseDto> searchItems(
            @RequestParam(defaultValue = "title") String title
    ) {
        SearchItemResponseDto responseDto = itemService.searchItems(title);
        return ResponseDto.success("아이템 검색 기능",responseDto);
    }

    @GetMapping("/category")
    public ResponseDto<SearchItemResponseDto> searchItemsByCategory(
            @RequestParam(defaultValue = "category") CategoryType category
    ) {
        SearchItemResponseDto responseDto = itemService.searchItemsByCategory(category);
        return ResponseDto.success("아이템 검색 기능",responseDto);
    }

    @PutMapping(value = "/{itemId}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<ItemResponseDto.EditItemResponseDto> editItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart @Valid ItemRequestDto.EditItemRequestDto requestDto,
            @RequestPart(value = "imgList", required = false) MultipartFile[] multipartFilesList
    ) {
        try {
            ItemResponseDto.EditItemResponseDto responseDto = itemService.editItem(itemId, userDetails.getUsername(), requestDto, multipartFilesList);
            return ResponseDto.success("아이템 수정 기능", responseDto);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("1장 이상의 이미지를 선택해 주세요.");
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseDto<Void> deleteItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        itemService.deleteItem(itemId, userDetails.getUsername());
        return ResponseDto.success("아이템 삭제 기능", null);
    }
}
