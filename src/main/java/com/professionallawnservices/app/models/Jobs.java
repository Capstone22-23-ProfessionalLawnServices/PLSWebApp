package com.professionallawnservices.app.models;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jobId")
    private long jobId;

    @Column(name = "startTime")
    private Time startTime;

    @Column(name = "endTime")
    private Time endTime;

    @Column(name = "totalTime")
    private Double totalTime;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "jobLocation")
    @NonNull
    private String jobLocation;

    @Column(name = "scheduledDate")
    @NonNull
    private Date scheduledDate;

    @ManyToOne
    @JoinColumn(name = "customnerId")
    private Customer customer;

    /*
    @OneToMany(mappedBy = "help")
    private Set<Help> help = new HashSet<>();

     */

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

    public double getCost() {
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

}
