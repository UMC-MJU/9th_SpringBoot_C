package com.example.umc9th.domain.store.repository;

import com.example.umc9th.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 메서드 이름으로 쿼리 생성
    List<Store> findByNameContaining(String keyword);

    List<Store> findByAddress(String address);

    Optional<Store> findByName(String name);

    // @Query 사용
    @Query("SELECT s FROM Store s WHERE s.address LIKE %:keyword%")
    List<Store> searchByAddress(@Param("keyword") String keyword);

    @Query("SELECT s FROM Store s JOIN FETCH s.location WHERE s.storeId = :storeId")
    Optional<Store> findByIdWithLocation(@Param("storeId") Long storeId);

    @Query("SELECT s FROM Store s WHERE s.name LIKE %:name% AND s.address LIKE %:address%")
    List<Store> searchByNameAndAddress(@Param("name") String name, @Param("address") String address);

    @Query("SELECT s FROM Store s WHERE s.location.locationId = :locationId")
    List<Store> findByLocationId(@Param("locationId") Long locationId);
}
