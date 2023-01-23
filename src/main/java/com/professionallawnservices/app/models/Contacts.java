package com.professionallawnservices.app.models;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contacts")
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactId")
    private long contactId;

    @Column(name = "contactName")
    @NonNull
    private String contactName;

    @Column(name = "contactPhone")
    @NonNull
    private String contactPhone;

    @Column(name = "contactEmail")
    @NonNull
    private String contactEmail;

    /*
    @OneToMany(mappedBy = "contacts")
    private Set<Help> help = new HashSet<>();

     */

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

}
