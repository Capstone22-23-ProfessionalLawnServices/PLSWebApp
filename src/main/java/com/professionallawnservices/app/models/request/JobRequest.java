package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.helpers.DateHelper;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class JobRequest {

    private long id;

    private double cost;

    private String endTime;

    private String startTime;

    private String location;

    private String scheduledDate;

    private int totalTime;

    private Customer customer;

    private ArrayList<Contact> contacts;

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
        scheduledDate = DateHelper.sqlDateToString(job.getScheduledDate());
        totalTime = (int) Math.round(job.getTotalTime());
        customer = job.getCustomer();
    }

    /*
    public JobRequest(Job job, Help help) {
        id = job.getJobId();
        cost = job.getCost();
        endTime = DateHelper.sqlDateToString(job.getScheduledDate(), job.getEndTime());
        startTime = DateHelper.sqlDateToString(job.getScheduledDate(), job.getStartTime());
        location = job.getJobLocation();
        scheduledDate = job.getScheduledDate();
        totalTime = (int) Math.round(job.getTotalTime());
        customer = job.getCustomer();
        contacts.add(help.getContact().getContactId());
    }

     */

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

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contactIds) {
        this.contacts = contactIds;
    }
}
