package com.professionallawnservices.app.models.request;

import com.professionallawnservices.app.models.data.Customer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CustomerRequest {

    private long id;

    private String name;

    private String location;

    private String phone;

    private String email;

    @NotNull
    @Min(0)
    private int frequency = 0;

    public CustomerRequest() {

    }

    public CustomerRequest(Long id) {
        this.id = id;
    }

    public CustomerRequest(Customer customer) {
        id = customer.getCustomerId();
        name = customer.getCustomerName();
        location = customer.getCustomerLocation();
        phone = customer.getCustomerPhone();
        frequency = customer.getFrequency();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
