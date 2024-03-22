package com.sparta.hhztclone.domain.item.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sparta.hhztclone.domain.item.entity.Item;
import com.sparta.hhztclone.global.config.QuerydslConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sparta.hhztclone.domain.item.entity.QItem.item;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final QuerydslConfig querydslConfig;

    @Override
    public List<Item> searchItems(String keyword) {
        return querydslConfig.jpaQueryFactory().selectFrom(item)
                .where(equalsType(keyword))
                .orderBy(item.createdAt.desc())
                .fetch();
    }

    private BooleanExpression equalsType(String keyword) {
        return item.title.contains(keyword);
    }
}
