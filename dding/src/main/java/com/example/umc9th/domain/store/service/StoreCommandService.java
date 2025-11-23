package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.StoreRequestDTO;
import com.example.umc9th.domain.store.entity.Store;

public interface StoreCommandService {
    Store createStore(StoreRequestDTO.CreateDTO request);
}
