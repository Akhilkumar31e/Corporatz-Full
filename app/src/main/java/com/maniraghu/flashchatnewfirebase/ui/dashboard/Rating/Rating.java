package com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating;

public class Rating {
    public String companyName,companyRating,userId;

    public Rating() {
    }

    public Rating(String companyName, String companyRating, String userId) {
        this.companyName = companyName;
        this.companyRating = companyRating;
        this.userId = userId;
    }
}
