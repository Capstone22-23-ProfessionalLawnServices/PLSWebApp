package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.models.data.Contact;

public class ContactRequest {

    private long id;

    private String email;

    private String name;

    private String phone;

    public ContactRequest() {

    }

    public ContactRequest(long id) {
        this.id = id;
    }

    public ContactRequest(Contact contact) {
        id = contact.getContactId();
        email = contact.getContactEmail();
        name = contact.getContactName();
        phone = contact.getContactPhone();
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
