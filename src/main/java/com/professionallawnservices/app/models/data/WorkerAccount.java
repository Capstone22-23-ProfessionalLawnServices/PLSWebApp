package com.professionallawnservices.app.models.data;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "WorkerAccount", uniqueConstraints = { @UniqueConstraint(columnNames = { "worker_id", "username" }) })
@Proxy(lazy=false)
public class WorkerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_account_id")
    private long workerAccountId;

    @OneToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @OneToOne
    @JoinColumn(name = "username")
    private Users users;

    public long getWorkerAccountId() {
        return workerAccountId;
    }

    public void setWorkerAccountId(long workerAccountId) {
        this.workerAccountId = workerAccountId;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
