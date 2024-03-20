package com.sparta.hhztclone.domain.item.controller.docs;

import com.sparta.hhztclone.domain.image.dto.ImageSaveDto;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemRequestDto.CreateItemRequestDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.CreateItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.GetItemResponseDto;
import com.sparta.hhztclone.domain.item.dto.ItemResponseDto.SearchItemResponseDto;
import com.sparta.hhztclone.global.dto.ResponseDto;
import com.sparta.hhztclone.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.sparta.hhztclone.domain.item.dto.ItemResponseDto.*;

@Tag(name = "items", description = "아이템 관련 API")
public interface ItemControllerDocs {

    @Operation(summary = "아이템 생성 기능", description = "아이템을 생성할 수 있는 API")
    ResponseDto<CreateItemResponseDto> createItem(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid CreateItemRequestDto requestDto,
            @ModelAttribute ImageSaveDto imageSaveDto
    );

    @Operation(summary = "아이템 목록 조회 기능", description = "아이템 목록을 조회할 수 있는 API")
    ResponseDto<List<SearchItemResponseDto>> getItems();

    @Operation(summary = "아이템 조회 기능", description = "아이템을 조회할 수 있는 API")
    ResponseDto<GetItemResponseDto> getItem(
            @PathVariable Long itemId
    );

    @Operation(summary = "아이템 수정 기능", description = "아이템을 수정할 수 있는 API")
    ResponseDto<EditItemResponseDto> editItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid ItemRequestDto.EditItemRequestDto requestDto
    );

    @Operation(summary = "아이템 삭제 기능", description = "아이템을 삭제할 수 있는 API")
    ResponseDto<Void> deleteItem(
            @PathVariable Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    );

    @Operation(summary = "아이템 검색 기능", description = "아이템을 검색할 수 있는 API")
    ResponseDto<List<SearchItemResponseDto>> searchItems(
            @RequestParam(defaultValue = "title") String type,
            @RequestParam String keyword
    );

}
