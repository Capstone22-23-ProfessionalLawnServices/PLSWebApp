package com.professionallawnservices.app.models;

import javax.persistence.*;

@Entity
@Table(name = "help")
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "helpId")
    private long helpId;

    @ManyToOne
    @JoinColumn(name = "contactId")
    private Contacts contacts;

    @ManyToOne
    @JoinColumn(name = "jobId")
    private Jobs job;


    public long getId() {
        return helpId;
    }

    public void setId(long helpId) {
        this.helpId = helpId;
    }

    /*
    public Contacts getContact() {
        return contact;
    }

    public void setContact(Contacts contact) {
        this.contact = contact;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

     */

}
