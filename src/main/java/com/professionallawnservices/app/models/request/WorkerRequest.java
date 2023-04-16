package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.models.data.Worker;

public class WorkerRequest {

    private long id = -1;

    private String email;

    private String name;

    private String phone;

    public WorkerRequest() {

    }

    public WorkerRequest(long id) {
        this.id = id;
    }

    public WorkerRequest(Worker worker) {
        id = worker.getWorkerId();
        email = worker.getWorkerEmail();
        name = worker.getWorkerName();
        phone = worker.getWorkerPhone();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
