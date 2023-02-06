package com.professionallawnservices.app.models.data;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "help")
@Proxy(lazy=false)
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "help_id")
    private long helpId;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public Help() {

    }

    public Help(Contact contact, Job job) {
        this.contact = contact;
        this.job = job;
    }

    public long getId() {
        return helpId;
    }

    public void setId(long helpId) {
        this.helpId = helpId;
    }

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
