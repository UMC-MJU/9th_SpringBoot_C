package com.example.umc.domain.baseaddress.repository;

import com.example.umc.domain.baseaddress.entity.BaseAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseAddressRepository extends JpaRepository<BaseAddress, Long> {

    /**
     * 주소명으로 BaseAddress 조회
     * @param baseAddressName 주소명
     * @return BaseAddress (Optional)
     */
    Optional<BaseAddress> findByBaseAddressName(String baseAddressName);
}
