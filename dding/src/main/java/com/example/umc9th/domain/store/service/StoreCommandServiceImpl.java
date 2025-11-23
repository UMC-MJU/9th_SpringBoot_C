package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.converter.StoreConverter;
import com.example.umc9th.domain.store.dto.StoreRequestDTO;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.LocationRepository;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.umc9th.domain.store.entity.Location;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;

    @Override
    public Store createStore(StoreRequestDTO.CreateDTO request) {
        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.NOT_FOUND));
        Store newStore = StoreConverter.toStore(request, location);
        return storeRepository.save(newStore);
    }
}
