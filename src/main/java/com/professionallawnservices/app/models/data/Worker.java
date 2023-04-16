package com.professionallawnservices.app.models.data;

import com.professionallawnservices.app.models.request.WorkerRequest;
import org.hibernate.annotations.Proxy;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "worker")
@Proxy(lazy=false)
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_id")
    private long workerId;

    @Column(name = "worker_name")
    @NonNull
    private String workerName;

    @Column(name = "worker_phone")
    @NonNull
    private String workerPhone;

    @Column(name = "worker_email")
    @NonNull
    private String workerEmail;

    @OneToMany(mappedBy = "worker")
    private Set<Help> help = new HashSet<>();

    public Worker() {

    }

    public Worker(WorkerRequest workerRequest) {
        workerId = workerRequest.getId();
        workerName = workerRequest.getName();
        workerPhone = workerRequest.getPhone();
        workerEmail = workerRequest.getEmail();
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public String getWorkerEmail() {
        return workerEmail;
    }

    public void setWorkerEmail(String workerEmail) {
        this.workerEmail = workerEmail;
    }

}
