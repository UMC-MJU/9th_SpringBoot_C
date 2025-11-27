package com.example.chapter4_umc.domain.review.dto;

public class ReviewDto {
    private Long reviewId;
    private Integer rating;
    private String content;
    private String storeName;
    private String imageUrl;

    public ReviewDto(Long reviewId, Integer rating, String content, String storeName, String imageUrl) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.content = content;
        this.storeName = storeName;
        this.imageUrl = imageUrl;
    }

    public Long getReviewId() {
        return reviewId;
    }
    public Integer getRating() {
        return rating;
    }
    public String getContent() {
        return content;
    }
    public String getStoreName() {
        return storeName;
    }
    public String getImageUrl() {
        return imageUrl;
    }

}
