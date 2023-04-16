package com.professionallawnservices.app.models.data;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "help", uniqueConstraints = { @UniqueConstraint(columnNames = { "worker_id", "job_id" }) })
@Proxy(lazy=false)
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "help_id")
    private long helpId;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public Help() {

    }

    public Help(Worker worker, Job job) {
        this.worker = worker;
        this.job = job;
    }

    public long getId() {
        return helpId;
    }

    public void setId(long helpId) {
        this.helpId = helpId;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

}
