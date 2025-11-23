package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewRequestDTO;
import com.example.umc9th.domain.review.dto.ReviewResponseDTO;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.service.ReviewCommandService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/reviews")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;

    @PostMapping("/")
    public ApiResponse<ReviewResponseDTO.CreateResultDTO> create(
            @PathVariable Long storeId,
            @RequestBody ReviewRequestDTO.CreateDTO request
    ){
        Review review = reviewCommandService.createReview(storeId, request);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, ReviewConverter.toCreateResultDTO(review));
    }
}
