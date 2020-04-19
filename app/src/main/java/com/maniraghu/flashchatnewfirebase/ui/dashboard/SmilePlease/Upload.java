package com.maniraghu.flashchatnewfirebase.ui.dashboard.SmilePlease;

public class Upload {
    private String mDesc;
    private String mImageUrl;
    private String mUsername;
    private String mId;
    private String mTime;
    private String mPostId;
    private String mLike;
    public Upload() {
        //empty constructor needed
    }

    public Upload(String mDesc, String mImageUrl, String mUsername, String mId, String mTime,String mPostId) {
        this.mDesc = mDesc;
        this.mImageUrl = mImageUrl;
        this.mUsername = mUsername;
        this.mId = mId;
        this.mTime = mTime;
        this.mPostId=mPostId;
    }

    public String getmLike() {
        return mLike;
    }

    public void setmLike(String mLike) {
        this.mLike = mLike;
    }

    public String getmPostId() {
        return mPostId;
    }

    public void setmPostId(String mPostId) {
        this.mPostId = mPostId;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}