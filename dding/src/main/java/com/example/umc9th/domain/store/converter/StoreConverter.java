package com.example.umc9th.domain.store.converter;

import com.example.umc9th.domain.store.dto.StoreRequestDTO;
import com.example.umc9th.domain.store.dto.StoreResponseDTO;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.entity.Store;
import java.time.LocalDateTime;

public class StoreConverter {

    public static StoreResponseDTO.CreateResultDTO toCreateResultDTO(Store store){
        return StoreResponseDTO.CreateResultDTO.builder()
                .storeId(store.getStoreId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Store toStore(StoreRequestDTO.CreateDTO request, Location location){
        return Store.builder()
                .name(request.getName())
                .managerNumber(request.getManagerNumber())
                .address(request.getAddress())
                .location(location)
                .build();
    }
}
