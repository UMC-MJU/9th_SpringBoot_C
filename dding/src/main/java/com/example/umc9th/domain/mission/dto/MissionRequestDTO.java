package com.example.umc9th.domain.mission.dto;

import java.time.LocalDate;
import lombok.Getter;

public class MissionRequestDTO {

    @Getter
    public static class CreateDTO{
        private LocalDate deadline;
        private String conditional;
        private Integer point;
        private Long storeId;
    }
}
