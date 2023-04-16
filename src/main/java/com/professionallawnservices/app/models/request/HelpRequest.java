package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.models.data.Worker;
import com.professionallawnservices.app.models.data.Job;

public class HelpRequest {

    private Worker worker;

    private Job job;

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
}
