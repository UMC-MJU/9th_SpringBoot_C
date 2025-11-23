package com.example.umc9th.domain.store.repository;

import com.example.umc9th.domain.store.entity.Store;
import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findStoresByName(String name);
}
