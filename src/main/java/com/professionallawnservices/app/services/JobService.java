package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.models.request.CustomerRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.repos.CustomerRepo;
import com.professionallawnservices.app.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    JobRepo jobRepo;

    @Autowired
    CustomerRepo customerRepo;

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

            result.setData(new JobRequest(job));
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
            Job job = new Job(jobRequest, customer);

            jobRepo.save(job);

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
            Job job = new Job(jobRequest, customer);

            jobRepo.save(job);

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
            jobRepo.delete(job);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting the job with id: " + jobRequest.getId());
        }

        return result;

    }


}
