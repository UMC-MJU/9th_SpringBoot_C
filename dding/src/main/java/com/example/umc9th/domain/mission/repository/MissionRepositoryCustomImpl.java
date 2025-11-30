package com.example.umc9th.domain.mission.repository;

import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.QMission;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryCustomImpl implements MissionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Mission> findAllByStoreId(Long storeId, Pageable pageable) {
        List<Mission> content = queryFactory
                .selectFrom(QMission.mission)
                .where(QMission.mission.store.storeId.eq(storeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(QMission.mission)
                .where(QMission.mission.store.storeId.eq(storeId))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}
