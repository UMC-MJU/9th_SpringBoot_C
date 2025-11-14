package com.example.chapter4_umc.domain.review.dto;

public class ReviewSearchCondition {
    private String storeName;
    private String ratingRange;

    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public  String getRatingRange() {
        return ratingRange;
    }
    public void setRatingRange(String ratingRange) {
        this.ratingRange = ratingRange;
    }
}
