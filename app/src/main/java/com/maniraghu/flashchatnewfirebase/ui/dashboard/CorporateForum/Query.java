package com.maniraghu.flashchatnewfirebase.ui.dashboard.CorporateForum;

public class Query {
    private String qQuery;
    private String qQueryId;
    private String qUsername;
    private String qTime;
    private String qId;

    public Query() {
    }

    public Query(String qQuery, String qQueryId, String qUsername, String qTime, String qId) {
        this.qQuery = qQuery;
        this.qQueryId = qQueryId;
        this.qUsername = qUsername;
        this.qTime = qTime;
        this.qId = qId;
    }

    public String getqQuery() {
        return qQuery;
    }

    public void setqQuery(String qQuery) {
        this.qQuery = qQuery;
    }

    public String getqQueryId() {
        return qQueryId;
    }

    public void setqQueryId(String qQueryId) {
        this.qQueryId = qQueryId;
    }

    public String getqUsername() {
        return qUsername;
    }

    public void setqUsername(String qUsername) {
        this.qUsername = qUsername;
    }

    public String getqTime() {
        return qTime;
    }

    public void setqTime(String qTime) {
        this.qTime = qTime;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }
}
