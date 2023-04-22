package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.helpers.DateHelper;
import com.professionallawnservices.app.models.data.Worker;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;

import java.util.ArrayList;

public class JobRequest {

    private long id;

    private double cost;

    private String endTime;

    private String startTime;

    private String location;

    private String scheduledDate;

    private int totalTime;

    private String notes;

    private Customer customer;

    private ArrayList<Worker> workers;

    public JobRequest() {

    }

    public JobRequest(long id) {
        this.id = id;
    }

    public JobRequest(Job job) {
        id = job.getJobId();
        cost = job.getCost();
        endTime = DateHelper.sqlTimeToString(job.getScheduledDate(), job.getEndTime());
        startTime = DateHelper.sqlTimeToString(job.getScheduledDate(), job.getStartTime());
        location = job.getJobLocation();
        notes = job.getNotes();
        scheduledDate = DateHelper.sqlDateToString(job.getScheduledDate());
        totalTime = (int) Math.round(job.getTotalTime());
        customer = job.getCustomer();
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

    //@NotNull
    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Worker> workerIds) {
        this.workers = workerIds;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
