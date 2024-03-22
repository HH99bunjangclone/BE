package com.sparta.hhztclone.domain.item.repository;

import com.sparta.hhztclone.domain.item.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> searchItems(String keyword);
}
