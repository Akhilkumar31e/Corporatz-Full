package com.maniraghu.flashchatnewfirebase.ui.home;

public class SearchUserInfo {
    private String username,userId;

    public SearchUserInfo() {
    }

    public SearchUserInfo(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
