package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

public class Ride {
    public String userId,nameOfPooler,freeOrD,cost,gender,type,source,destination,time,mobile;
    public Ride(){

    }

    public Ride(String userId,String nameOfPooler, String freeOrD, String cost, String gender, String type, String source, String destination, String time, String mobile) {
        this.userId=userId;
        this.nameOfPooler = nameOfPooler;
        this.freeOrD = freeOrD;
        this.cost = cost;
        this.gender = gender;
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.mobile = mobile;
    }
}
