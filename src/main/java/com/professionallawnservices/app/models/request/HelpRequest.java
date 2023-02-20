package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.helpers.DateHelper;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;

import java.util.ArrayList;

public class HelpRequest {

    private Contact contact;

    private Job job;

    public HelpRequest() {

    }

    public HelpRequest(Contact contact, Job job) {
        this.contact = contact;
        this.job = job;
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
