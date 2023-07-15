package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.models.data.Worker;
import com.professionallawnservices.app.models.data.Job;

public class HelpRequest {

    private long id;

    private Worker worker;

    private Job job;

    private Double workerPay;

    public HelpRequest() {

    }

    public HelpRequest(Worker worker, Job job) {
        this.worker = worker;
        this.job = job;
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

    public Double getWorkerPay() {
        return workerPay;
    }

    public void setWorkerPay(Double workerPay) {
        this.workerPay = workerPay;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
