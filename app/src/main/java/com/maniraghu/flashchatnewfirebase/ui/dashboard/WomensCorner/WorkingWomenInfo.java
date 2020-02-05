package com.maniraghu.flashchatnewfirebase.ui.dashboard.WomensCorner;

public class WorkingWomenInfo {
    private String postTitle;
    private String postDesc;
    private String postUrl;

    public WorkingWomenInfo() {
    }

    public WorkingWomenInfo(String postTitle, String postDesc, String postUrl) {
        this.postTitle = postTitle;
        this.postDesc = postDesc;
        this.postUrl = postUrl;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
}
