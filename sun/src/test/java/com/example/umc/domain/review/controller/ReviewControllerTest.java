package com.example.umc.domain.review.controller;

import com.example.umc.domain.review.dto.request.ReviewRequestDto;
import com.example.umc.domain.review.dto.response.ReviewResponseDto;
import com.example.umc.domain.review.service.ReviewService;
import com.example.umc.domain.review.validator.ReviewFilterValidator;
import com.example.umc.global.exception.CustomException;
import com.example.umc.global.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@DisplayName("리뷰 Controller 테스트")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReviewFilterValidator reviewFilterValidator;

    @Test
    @DisplayName("리뷰 작성 성공")
    void createReview_Success() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(1L)
                .restaurantId(1L)
                .reviewContent("음식이 정말 맛있어요!")
                .starRating(new BigDecimal("4.5"))
                .build();

        ReviewResponseDto.ReviewCreateDto response = ReviewResponseDto.ReviewCreateDto.builder()
                .reviewId(1L)
                .memberName("홍길동")
                .starRating(new BigDecimal("4.5"))
                .reviewContent("음식이 정말 맛있어요!")
                .createdAt("2025.11.22")
                .build();

        given(reviewService.createReview(any(ReviewRequestDto.CreateReviewDto.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("COMMON201"))
                .andExpect(jsonPath("$.result.reviewId").value(1L))
                .andExpect(jsonPath("$.result.memberName").value("홍길동"))
                .andExpect(jsonPath("$.result.reviewContent").value("음식이 정말 맛있어요!"))
                .andExpect(jsonPath("$.result.starRating").value(4.5));
    }

    @Test
    @DisplayName("리뷰 작성 실패 - memberId null")
    void createReview_Fail_MemberIdNull() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(null)  // null
                .restaurantId(1L)
                .reviewContent("음식이 정말 맛있어요!")
                .starRating(new BigDecimal("4.5"))
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("리뷰 작성 실패 - restaurantId null")
    void createReview_Fail_RestaurantIdNull() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(1L)
                .restaurantId(null)  // null
                .reviewContent("음식이 정말 맛있어요!")
                .starRating(new BigDecimal("4.5"))
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("리뷰 작성 실패 - reviewContent 빈 문자열")
    void createReview_Fail_ReviewContentBlank() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(1L)
                .restaurantId(1L)
                .reviewContent("")  // 빈 문자열
                .starRating(new BigDecimal("4.5"))
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("리뷰 작성 실패 - starRating null")
    void createReview_Fail_StarRatingNull() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(1L)
                .restaurantId(1L)
                .reviewContent("음식이 정말 맛있어요!")
                .starRating(null)  // null
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("리뷰 작성 실패 - starRating 범위 초과 (5.0 초과)")
    void createReview_Fail_StarRatingExceedsMax() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(1L)
                .restaurantId(1L)
                .reviewContent("음식이 정말 맛있어요!")
                .starRating(new BigDecimal("5.5"))  // 5.0 초과
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("리뷰 작성 실패 - starRating 범위 미만 (0.0 미만)")
    void createReview_Fail_StarRatingBelowMin() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(1L)
                .restaurantId(1L)
                .reviewContent("음식이 정말 맛있어요!")
                .starRating(new BigDecimal("-1.0"))  // 0.0 미만
                .build();

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("리뷰 작성 실패 - 존재하지 않는 회원")
    void createReview_Fail_MemberNotFound() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(999L)  // 존재하지 않는 회원
                .restaurantId(1L)
                .reviewContent("음식이 정말 맛있어요!")
                .starRating(new BigDecimal("4.5"))
                .build();

        given(reviewService.createReview(any(ReviewRequestDto.CreateReviewDto.class)))
                .willThrow(new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("MEMBER-001"));
    }

    @Test
    @DisplayName("리뷰 작성 실패 - 존재하지 않는 레스토랑")
    void createReview_Fail_RestaurantNotFound() throws Exception {
        // given
        ReviewRequestDto.CreateReviewDto request = ReviewRequestDto.CreateReviewDto.builder()
                .memberId(1L)
                .restaurantId(999L)  // 존재하지 않는 레스토랑
                .reviewContent("음식이 정말 맛있어요!")
                .starRating(new BigDecimal("4.5"))
                .build();

        given(reviewService.createReview(any(ReviewRequestDto.CreateReviewDto.class)))
                .willThrow(new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        // when & then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value("RESTAURANT-001"));
    }

    @Test
    @DisplayName("내 리뷰 조회 성공")
    void getMyReviews_Success() throws Exception {
        // given
        ReviewResponseDto.MyReviewDto review1 = ReviewResponseDto.MyReviewDto.builder()
                .reviewId(1L)
                .restaurantName("맛있는 식당")
                .memberName("홍길동")
                .starRating(new BigDecimal("4.5"))
                .reviewContent("음식이 맛있어요")
                .createdAt("2025.11.22")
                .build();

        ReviewResponseDto.MyReviewDto review2 = ReviewResponseDto.MyReviewDto.builder()
                .reviewId(2L)
                .restaurantName("좋은 식당")
                .memberName("홍길동")
                .starRating(new BigDecimal("5.0"))
                .reviewContent("서비스가 좋아요")
                .createdAt("2025.11.21")
                .build();

        Page<ReviewResponseDto.MyReviewDto> reviewPage = new PageImpl<>(
                List.of(review1, review2)
        );

        ReviewResponseDto.MyReviewListDto response = ReviewResponseDto.MyReviewListDto.builder()
                .reviews(List.of(review1, review2))
                .currentPage(0)
                .totalPages(1)
                .totalElements(2L)
                .hasNext(false)
                .build();

        given(reviewService.getMyReviews(eq(1L), any(), any()))
                .willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/reviews/my")
                        .header("X-Member-Id", "1")  // LoginMemberId mock
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.reviews").isArray())
                .andExpect(jsonPath("$.result.reviews.length()").value(2))
                .andExpect(jsonPath("$.result.currentPage").value(0))
                .andExpect(jsonPath("$.result.totalElements").value(2));
    }

    @Test
    @DisplayName("내 리뷰 조회 성공 - 필터링 적용")
    void getMyReviews_Success_WithFilters() throws Exception {
        // given
        ReviewResponseDto.MyReviewDto review = ReviewResponseDto.MyReviewDto.builder()
                .reviewId(1L)
                .restaurantName("맛있는 식당")
                .memberName("홍길동")
                .starRating(new BigDecimal("4.5"))
                .reviewContent("음식이 맛있어요")
                .createdAt("2025.11.22")
                .build();

        ReviewResponseDto.MyReviewListDto response = ReviewResponseDto.MyReviewListDto.builder()
                .reviews(List.of(review))
                .currentPage(0)
                .totalPages(1)
                .totalElements(1L)
                .hasNext(false)
                .build();

        given(reviewService.getMyReviews(eq(1L), any(), any()))
                .willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/reviews/my")
                        .header("X-Member-Id", "1")
                        .param("restaurantName", "맛있는 식당")
                        .param("minStarRating", "4.0")
                        .param("maxStarRating", "5.0")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.reviews").isArray());
    }

    @Test
    @DisplayName("내 리뷰 조회 실패 - 페이지 번호 음수")
    void getMyReviews_Fail_NegativePage() throws Exception {
        // when & then
        mockMvc.perform(get("/api/v1/reviews/my")
                        .header("X-Member-Id", "1")
                        .param("page", "-1")  // 음수
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("내 리뷰 조회 실패 - 페이지 크기 0 이하")
    void getMyReviews_Fail_InvalidPageSize() throws Exception {
        // when & then
        mockMvc.perform(get("/api/v1/reviews/my")
                        .header("X-Member-Id", "1")
                        .param("page", "0")
                        .param("size", "0"))  // 0
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("내 리뷰 조회 실패 - 페이지 크기 100 초과")
    void getMyReviews_Fail_PageSizeExceedsMax() throws Exception {
        // when & then
        mockMvc.perform(get("/api/v1/reviews/my")
                        .header("X-Member-Id", "1")
                        .param("page", "0")
                        .param("size", "101"))  // 100 초과
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false));
    }
}
