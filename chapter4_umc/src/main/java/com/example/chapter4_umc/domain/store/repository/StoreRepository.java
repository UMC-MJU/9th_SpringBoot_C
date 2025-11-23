package com.example.chapter4_umc.domain.store.repository;

import com.example.chapter4_umc.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
