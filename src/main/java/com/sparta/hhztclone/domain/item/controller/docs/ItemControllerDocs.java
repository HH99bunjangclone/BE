package com.sparta.hhztclone.domain.item.controller.docs;

import com.sparta.hhztclone.domain.item.dto.ItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.CreateItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.GetItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.SearchItemResponseDto;
import com.sparta.hhztclone.domain.item.entity.Category.CategoryType;
import com.sparta.hhztclone.global.dto.ResponseDto;
import com.sparta.hhztclone.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static com.sparta.hhztclone.domain.item.dto.ItemResponseDto.EditItemResponseDto;

@Tag(name = "items", description = "아이템 관련 API")
public interface ItemControllerDocs {

    @Operation(summary = "아이템 생성 기능", description = "아이템을 생성할 수 있는 API")
    ResponseDto<CreateItemResponseDto> createItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart @Valid ItemRequestDto.CreateItemRequestDto requestDto,
            @RequestPart(value = "imgList", required = false) MultipartFile[] multipartFilesList
    );

    @Operation(summary = "아이템 목록 조회 기능", description = "아이템 목록을 조회할 수 있는 API")
    ResponseDto<SearchItemResponseDto> getItems();

    @Operation(summary = "아이템 조회 기능", description = "아이템을 조회할 수 있는 API")
    ResponseDto<GetItemResponseDto> getItem(
            @PathVariable Long itemId
    );

    @Operation(summary = "아이템 수정 기능", description = "아이템을 수정할 수 있는 API")
    ResponseDto<EditItemResponseDto> editItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart @Valid ItemRequestDto.EditItemRequestDto requestDto,
            @RequestPart(value = "imgList", required = false) MultipartFile[] multipartFilesList
    );

    @Operation(summary = "아이템 삭제 기능", description = "아이템을 삭제할 수 있는 API")
    ResponseDto<Void> deleteItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    );

    @Operation(summary = "아이템 검색 기능", description = "아이템을 검색할 수 있는 API")
    ResponseDto<SearchItemResponseDto> searchItems(
            @RequestParam(defaultValue = "title") String keyword
    );

    @Operation(summary = "카테고리별 아이템 검색 기능", description = "카테고리별로 아이템을 조회할 수 있는 API")
    ResponseDto<SearchItemResponseDto> searchItemsByCategory(
            @RequestParam(defaultValue = "category") CategoryType category
    );

}
