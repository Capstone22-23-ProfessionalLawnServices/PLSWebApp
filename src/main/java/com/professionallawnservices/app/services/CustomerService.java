package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.CustomerRequest;
import com.professionallawnservices.app.repos.CustomerRepo;
import com.professionallawnservices.app.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    JobRepo jobRepo;

    public Result getAllCustomersPageable(Pageable pageable) {

        Result result = new Result();

        try{

            Page<Customer> customerPage = customerRepo.findAll(pageable);
            ArrayList<Customer> customerArrayList = new ArrayList<Customer>(customerPage.getContent());

            result.setData(customerArrayList);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue retrieving customers.");
        }

        return result;
    }

    public Result getAllCustomers() {

        Result result = new Result();

        try{

            result.setData(customerRepo.findAll());
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue retrieving customers.");
        }

        return result;
    }

    public Result getCustomerById(CustomerRequest customerRequest) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.findById(customerRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + customerRequest.getId()));

            result.setData(customer);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding the customer with id: " + customerRequest.getId());
        }

        return result;
    }

    public Result createCustomer(CustomerRequest customerRequest) {

        Result result = new Result();

        try {

            Customer customer = new Customer(customerRequest);

            customerRepo.save(customer);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue saving the customer with name: " + customerRequest.getName());
        }

        return result;
    }

    public Result updateCustomer(CustomerRequest customerRequest) {

        Result result = new Result();

        try {

            Customer customer = new Customer(customerRequest);

            customerRepo.save(customer);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue updating the customer with id: " + customerRequest.getId());
        }

        return result;
    }

    public Result deleteCustomerById(CustomerRequest customerRequest) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.getReferenceById(customerRequest.getId());

            ArrayList<Job> jobArrayList = jobRepo.getAllJobByCustomerId(customer.getCustomerId());

            jobRepo.deleteAll(jobArrayList);
            customerRepo.delete(customer);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting the customer with id: " + customerRequest.getId());
        }

        return result;
    }

}
