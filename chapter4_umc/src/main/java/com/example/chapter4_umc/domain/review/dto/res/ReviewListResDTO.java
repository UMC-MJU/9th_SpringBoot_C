package com.example.chapter4_umc.domain.review.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ReviewListResDTO {
    private List<ReviewSimpleResDTO> reviews;
    private Integer totalCount;
}
