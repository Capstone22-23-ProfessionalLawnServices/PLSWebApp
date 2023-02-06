package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.repos.ContactRepo;
import com.professionallawnservices.app.repos.CustomerRepo;
import com.professionallawnservices.app.repos.HelpRepo;
import com.professionallawnservices.app.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    JobRepo jobRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    HelpRepo helpRepo;

    public Result getAllJobs() {

        Result result = new Result();

        try{

            result.setData(jobRepo.findAll());

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue retrieving jobs.");
        }

        return result;
    }

    public Result getJobById(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobRequest.getId()));
            ArrayList<Help> helpArrayList = helpRepo.getAllHelpByJobId(job.getJobId());

            if (helpArrayList.size() > 0) {
                result.setData(new JobRequest(job, helpArrayList.get(0)));
            }
            else {
                result.setData(new JobRequest(job));
            }

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding the job with id: " + jobRequest.getId());
        }

        return result;
    }

    public Result createJob(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.findById(jobRequest.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" +
                            jobRequest.getCustomerId()));
            List<Contact> contacts = contactRepo.findAllById(jobRequest.getContactIds());
            Job job = new Job(jobRequest, customer);
            ArrayList<Help> helpArrayList = new ArrayList<Help>();

            for (Contact contact :
                    contacts) {
                helpArrayList.add(new Help(contact, job));
            }

            jobRepo.save(job);
            helpRepo.saveAll(helpArrayList);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue saving the job with customer id: " + jobRequest.getCustomerId());
        }

        return result;
    }

    public Result updateJob(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.findById(jobRequest.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" +
                            jobRequest.getCustomerId()));
            List<Contact> contacts = contactRepo.findAllById(jobRequest.getContactIds());
            Job job = new Job(jobRequest, customer);
            ArrayList<Help> helpArrayList = new ArrayList<Help>();

            for (Contact contact :
                    contacts) {
                helpArrayList.add(new Help(contact, job));
            }

            jobRepo.save(job);
            helpRepo.saveAll(helpArrayList);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue updating the job with id: " + jobRequest.getId());
        }

        return result;
    }

    public Result deleteJob(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobRequest.getId()));
            ArrayList<Help> helpArrayList = helpRepo.getAllHelpByJobId(job.getJobId());

            helpRepo.deleteAll(helpArrayList);
            jobRepo.delete(job);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting the job with id: " + jobRequest.getId());
        }

        return result;
    }

    public Result getCustomerByIdList(Long customerId) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.findById(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + customerId));

            result.setData(customer);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue getting customer with id: " + customerId);
        }

        return result;
    }

    public Result getContactsByIdList(ArrayList<Long> contactIds) {

        Result result = new Result();

        try {

            List<Contact> contacts = contactRepo.findAllById(contactIds);

            result.setData(contacts);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue getting contacts with ids: " + contactIds);
        }

        return result;
    }

    public Result getCustomerByJob(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.findById(jobRequest.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + jobRequest.getCustomerId()));

            result.setData(customer);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue getting customer with id: " + jobRequest.getCustomerId());
        }

        return result;
    }

    public Result getContactsByJob(JobRequest jobRequest) {

        Result result = new Result();

        try {

            if (jobRequest.getContactIds() == null) {
                result.setComplete(true);
                return result;
            }

            List<Contact> contacts = contactRepo.findAllById(jobRequest.getContactIds());

            result.setData(contacts);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue getting customers with ids: " + jobRequest.getContactIds());
        }

        return result;
    }

    public ArrayList<Contact> combineContactArrayLists(ArrayList<Contact> list1, ArrayList<Contact> list2) {

        ArrayList<Contact> combinedList = new ArrayList<Contact>();

        if (list1 != null) {
            combinedList.addAll(list1);
        }

        if (list2 != null) {
            combinedList.addAll(list2);
        }

        return combinedList;
    }


}
