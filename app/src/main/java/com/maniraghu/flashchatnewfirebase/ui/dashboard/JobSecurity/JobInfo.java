package com.maniraghu.flashchatnewfirebase.ui.dashboard.JobSecurity;

public class JobInfo {
    public String jobName,companyName,salary,contact;

    public JobInfo() {
    }

    public JobInfo(String jobName, String companyName, String salary, String contact) {
        this.jobName = jobName;
        this.companyName = companyName;
        this.salary = salary;
        this.contact = contact;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
