package com.maniraghu.flashchatnewfirebase.ui.dashboard.PartnerWithUs;

public class PartnerInformation {
    public String cName,cEmail,cMobile,cTime;

    public PartnerInformation() {
    }

    public PartnerInformation(String cName, String cEmail, String cMobile, String cTime) {
        this.cName = cName;
        this.cEmail = cEmail;
        this.cMobile = cMobile;
        this.cTime = cTime;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getcMobile() {
        return cMobile;
    }

    public void setcMobile(String cMobile) {
        this.cMobile = cMobile;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }
}
