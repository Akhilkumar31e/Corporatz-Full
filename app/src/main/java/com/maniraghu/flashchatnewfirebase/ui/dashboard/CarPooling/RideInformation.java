package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

public class RideInformation {
    private String cost,destination,freeOrD,gender,mobile,nameOfPooler,source,time,type;
    public RideInformation() {
    }

    public RideInformation(String cost, String destination, String freeOrD, String gender, String mobile, String nameOfPooler, String source, String time, String type) {
        this.cost = cost;
        this.destination = destination;
        this.freeOrD = freeOrD;
        this.gender = gender;
        this.mobile = mobile;
        this.nameOfPooler = nameOfPooler;
        this.source = source;
        this.time = time;
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFreeOrD() {
        return freeOrD;
    }

    public void setFreeOrD(String freeOrD) {
        this.freeOrD = freeOrD;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNameOfPooler() {
        return nameOfPooler;
    }

    public void setNameOfPooler(String nameOfPooler) {
        this.nameOfPooler = nameOfPooler;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
