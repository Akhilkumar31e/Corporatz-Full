package com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating;

public class Rating {
    public String companyName,companyRating,userId,companyReview;

    public Rating() {
    }

    public Rating(String companyName, String companyRating, String userId, String companyReview) {
        this.companyName = companyName;
        this.companyRating = companyRating;
        this.userId = userId;
        this.companyReview = companyReview;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyRating() {
        return companyRating;
    }

    public void setCompanyRating(String companyRating) {
        this.companyRating = companyRating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyReview() {
        return companyReview;
    }

    public void setCompanyReview(String companyReview) {
        this.companyReview = companyReview;
    }
}
