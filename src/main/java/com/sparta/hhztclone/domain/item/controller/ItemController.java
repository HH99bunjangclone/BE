package com.sparta.hhztclone.domain.item.controller;

import com.sparta.hhztclone.domain.item.controller.docs.ItemControllerDocs;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto;
import com.sparta.hhztclone.global.dto.ResponseDto;
import com.sparta.hhztclone.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/item")
public class ItemController implements ItemControllerDocs {

    @PostMapping
    public ResponseDto<ItemResponseDto.CreateItemResponseDto> createItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid ItemRequestDto.CreateItemRequestDto requestDto
    ) {
        return null;
    }

    @GetMapping
    public ResponseDto<List<ItemResponseDto.SearchItemResponseDto>> getItems() {
        return null;
    }

    @GetMapping("/{itemId}")
    public ResponseDto<ItemResponseDto.GetItemResponseDto> getItem(
            @PathVariable Long itemId
    ) {
        return null;
    }

    @PutMapping("/{itemId}")
    public ResponseDto<ItemResponseDto.EditItemResponseDto> editItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid ItemRequestDto.EditItemRequestDto requestDto
    ) {
        return null;
    }

    @DeleteMapping("/{itemId}")
    public ResponseDto<Void> deleteItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return null;
    }

    @GetMapping("/search")
    public ResponseDto<List<ItemResponseDto.SearchItemResponseDto>> searchItems(
            @RequestParam(defaultValue = "title") String type,
            @RequestParam String keyword
    ) {
        return null;
    }
}
