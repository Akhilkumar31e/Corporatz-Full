package com.maniraghu.flashchatnewfirebase.ui.notifications;

public class Notification {

    private String title;
    private String date;

    public Notification() {
    }

    public Notification(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDate() {
        return date;
    }

    public String getDateString() {
        return this.date;
    }

    public String getTimeString() {
        return this.date;
    }

}
