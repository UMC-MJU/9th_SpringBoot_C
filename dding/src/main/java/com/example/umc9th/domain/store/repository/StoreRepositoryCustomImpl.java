package com.example.umc9th.domain.store.repository;

import com.example.umc9th.domain.store.entity.QStore;
import com.example.umc9th.domain.store.entity.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Store> findStoresByName(String name) {
        return queryFactory
                .selectFrom(QStore.store)
                .where(QStore.store.name.contains(name))
                .fetch();
    }
}
