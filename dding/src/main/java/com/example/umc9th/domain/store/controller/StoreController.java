package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.mission.converter.MissionConverter;
import com.example.umc9th.domain.mission.dto.MissionResponseDTO;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.service.MissionQueryService;
import com.example.umc9th.domain.store.converter.StoreConverter;
import com.example.umc9th.domain.store.dto.StoreRequestDTO;
import com.example.umc9th.domain.store.dto.StoreResponseDTO;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.service.StoreCommandService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.global.validation.annotation.CheckPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreCommandService storeCommandService;
    private final MissionQueryService missionQueryService;

    @PostMapping("/")
    public ApiResponse<StoreResponseDTO.CreateResultDTO> create(@RequestBody StoreRequestDTO.CreateDTO request){
        Store store = storeCommandService.createStore(request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, StoreConverter.toCreateResultDTO(store));
    }

    @GetMapping("/{storeId}/missions")
    @Operation(summary = "특정 가게의 미션 목록 조회 API", description = "특정 가게의 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다. page 번호는 1부터 시작합니다.")
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1 이상의 숫자를 입력해주세요.")
    })
    public ApiResponse<MissionResponseDTO.MissionPreviewListDTO> getMissionList(
            @PathVariable Long storeId,
            @CheckPage @RequestParam(name = "page") Integer page
    ) {
        Page<Mission> missionList = missionQueryService.getMissionList(storeId, page);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, MissionConverter.toMissionPreviewListDTO(missionList));
    }
}
