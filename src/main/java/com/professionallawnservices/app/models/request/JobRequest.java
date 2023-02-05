package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.models.data.Job;

import java.sql.Date;
import java.sql.Time;

public class JobRequest {

    private long id;

    private double cost;

    private Time endTime;

    private Time startTime;

    private String location;

    private Date scheduledDate;

    private int totalTime;

    private long customerId;

    public JobRequest() {

    }

    public JobRequest(long id) {
        this.id = id;
    }

    public JobRequest(Job job) {
        id = job.getJobId();
        cost = job.getCost();
        endTime = job.getEndTime();
        startTime = job.getStartTime();
        location = job.getJobLocation();
        scheduledDate = job.getScheduledDate();
        totalTime = (int) Math.round(job.getTotalTime());
        customerId = job.getCustomer().getCustomerId();
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

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
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

}
