package com.maniraghu.flashchatnewfirebase.ui.home;

public class NewsInformation {
    private String desc;
    private String imgurl;
    private String title;

    public NewsInformation() {

    }
    public NewsInformation(String desc,String title,String imageUrl){
        this.desc=desc;
        this.imgurl=imageUrl;
        this.title=title;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    public String getImgurl() {
        return imgurl;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }



}
