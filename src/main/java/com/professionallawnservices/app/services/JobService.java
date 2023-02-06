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
            ArrayList<Help> help = helpRepo.getAllHelpByJobId(job.getJobId());

            result.setData(new JobRequest(job, help.get(0)));
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
            Contact contact = contactRepo.findById(jobRequest.getContactId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid help Id:" +
                            jobRequest.getContactId()));
            Job job = new Job(jobRequest, customer);
            Help help = new Help(contact, job);

            jobRepo.save(job);
            helpRepo.save(help);

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
            Contact contact = contactRepo.findById(jobRequest.getContactId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" +
                            jobRequest.getContactId()));
            Job job = new Job(jobRequest, customer);
            Help help = new Help(contact, job);

            jobRepo.save(job);
            helpRepo.save(help);

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


}
