package com.professionallawnservices.app.models.data;

import com.professionallawnservices.app.helpers.DateHelper;
import com.professionallawnservices.app.models.request.JobRequest;
import org.hibernate.annotations.Proxy;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job")
@Proxy(lazy=false)
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private long jobId;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "total_time")
    private Double totalTime;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "job_location")
    @NonNull
    private String jobLocation;

    @Column(name = "scheduled_date")
    @NonNull
    @NotNull
    private Date scheduledDate;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "job")
    private Set<Help> help = new HashSet<>();

    public Job() {

    }

    public Job(JobRequest jobRequest) {
        jobId = jobRequest.getId();
        startTime = DateHelper.stringToSqlTime(jobRequest.getStartTime());
        endTime = DateHelper.stringToSqlTime(jobRequest.getEndTime());
        totalTime = (double) jobRequest.getTotalTime();
        cost = jobRequest.getCost();
        notes = jobRequest.getNotes();
        jobLocation = jobRequest.getLocation();
        scheduledDate = DateHelper.stringToSqlDate(jobRequest.getScheduledDate());
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long id) {
        this.jobId = id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setScheduledDateFromString(String scheduledDateString) {
        scheduledDate = DateHelper.stringToSqlDate(scheduledDateString);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<Help> getHelp() {
        ArrayList<Help> helpList = new ArrayList<Help>();
        helpList.addAll(help);

        return helpList;
    }
}
