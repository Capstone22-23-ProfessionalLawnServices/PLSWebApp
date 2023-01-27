package com.professionallawnservices.app.models;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private long contactId;

    @Column(name = "contact_name")
    @NonNull
    private String contactName;

    @Column(name = "contact_phone")
    @NonNull
    private String contactPhone;

    @Column(name = "contact_email")
    @NonNull
    private String contactEmail;

    @OneToMany(mappedBy = "contact")
    private Set<Help> help = new HashSet<>();

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
