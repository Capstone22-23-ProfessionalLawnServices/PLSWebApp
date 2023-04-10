package com.professionallawnservices.app.models.data;

import com.professionallawnservices.app.models.request.CustomerRequest;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@Proxy(lazy=false)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_location")
    private String customerLocation;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "frequency")
    private int frequency;

    @OneToMany(mappedBy = "customer")
    private Set<Job> jobs = new HashSet<>();

    public Customer() {

    }

    public Customer(CustomerRequest customerRequest) {
        customerId = customerRequest.getId();
        customerName = customerRequest.getName();
        customerLocation = customerRequest.getLocation();
        customerPhone = customerRequest.getPhone();
        frequency = customerRequest.getFrequency();
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(String customerLocation) {
        this.customerLocation = customerLocation;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}
