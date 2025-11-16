package com.example.umc9th.domain.store.dto;

import lombok.Getter;

public class StoreRequestDTO {

    @Getter
    public static class CreateDTO{
        private String name;
        private Long managerNumber;
        private String address;
        private Long locationId;
    }
}
