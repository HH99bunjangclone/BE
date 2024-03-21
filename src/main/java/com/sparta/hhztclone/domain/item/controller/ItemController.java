package com.sparta.hhztclone.domain.item.controller;

import com.sparta.hhztclone.domain.image.dto.ImageSaveDto;
import com.sparta.hhztclone.domain.item.controller.docs.ItemControllerDocs;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto;
import com.sparta.hhztclone.domain.item.service.ItemService;
import com.sparta.hhztclone.global.dto.ResponseDto;
import com.sparta.hhztclone.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/item")
@DynamicUpdate
@Slf4j
public class ItemController implements ItemControllerDocs {

    private final ItemService itemService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<ItemResponseDto.CreateItemResponseDto> createItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart @Valid ItemRequestDto.CreateItemRequestDto requestDto,
            @RequestPart(value = "files", required = false) MultipartFile[] multipartFilesList

    ) {
        ItemResponseDto.CreateItemResponseDto responseDto = itemService.createItem(userDetails.getUsername(), requestDto, multipartFilesList);
        return ResponseDto.success("아이템 생성 기능", responseDto);
    }

    @GetMapping
    public ResponseDto<List<ItemResponseDto.SearchItemResponseDto>> getItems() {
        return null;
    }

    @GetMapping("/{itemId}")
    public ResponseDto<ItemResponseDto.GetItemResponseDto> getItem(
            @PathVariable Long itemId
    ) {
        ItemResponseDto.GetItemResponseDto responseDto = itemService.getItem(itemId);
        return ResponseDto.success("아이템 조회 기능", responseDto);
    }

    @PutMapping(value = "/{itemId}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE}) //@PatchMapping
    public ResponseDto<ItemResponseDto.EditItemResponseDto> editItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart @Valid ItemRequestDto.EditItemRequestDto requestDto,
            @RequestPart(value = "files", required = false) MultipartFile[] multipartFilesList
    ) {
        ItemResponseDto.EditItemResponseDto responseDto = itemService.editItem(itemId, userDetails.getUsername(), requestDto, multipartFilesList);
        return ResponseDto.success("아이템 수정 기능", responseDto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseDto<Void> deleteItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        itemService.deleteItem(itemId, userDetails.getUsername());
        return ResponseDto.success("아이템 삭제 기능", null);
    }

    @GetMapping("/search")
    public ResponseDto<List<ItemResponseDto.SearchItemResponseDto>> searchItems(
            @RequestParam(defaultValue = "title") String type,
            @RequestParam String keyword
    ) {
        return null;
    }

}
