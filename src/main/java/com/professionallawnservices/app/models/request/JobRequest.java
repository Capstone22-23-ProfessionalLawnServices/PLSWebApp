package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.helpers.DateHelper;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;

import java.sql.Date;

public class JobRequest {

    private long id;

    private double cost;

    private String endTime;

    private String startTime;

    private String location;

    private Date scheduledDate;

    private int totalTime;

    private long customerId;

    private long contactId;

    public JobRequest() {

    }

    public JobRequest(long id) {
        this.id = id;
    }

    public JobRequest(Job job) {
        id = job.getJobId();
        cost = job.getCost();
        endTime = DateHelper.sqlDateToString(job.getScheduledDate(), job.getEndTime());
        startTime = DateHelper.sqlDateToString(job.getScheduledDate(), job.getStartTime());
        location = job.getJobLocation();
        scheduledDate = job.getScheduledDate();
        totalTime = (int) Math.round(job.getTotalTime());
        customerId = job.getCustomer().getCustomerId();
    }

    public JobRequest(Job job, Help help) {
        id = job.getJobId();
        cost = job.getCost();
        endTime = DateHelper.sqlDateToString(job.getScheduledDate(), job.getEndTime());
        startTime = DateHelper.sqlDateToString(job.getScheduledDate(), job.getStartTime());
        location = job.getJobLocation();
        scheduledDate = job.getScheduledDate();
        totalTime = (int) Math.round(job.getTotalTime());
        customerId = job.getCustomer().getCustomerId();
        contactId = help.getContact().getContactId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }
}
