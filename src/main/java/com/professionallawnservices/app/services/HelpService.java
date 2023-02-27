package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.ContactRequest;
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
public class HelpService {

    @Autowired
    HelpRepo helpRepo;

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    JobRepo jobRepo;

    public Result deleteHelpByContactRequestAndJobRequest(ContactRequest contactRequest, JobRequest jobRequest) {

        Result result = new Result();

        try{

            Contact contact = contactRepo.findById(contactRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + contactRequest.getId()));
            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobRequest.getId()));

            ArrayList<Help> helpArrayList = helpRepo.getHelpByContactIdAndJobId(
                    contact.getContactId(),
                    job.getJobId()
            );

            helpRepo.deleteAll(helpArrayList);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting help with contactId: " +
                    contactRequest.getId() + " and jobId: " + jobRequest.getId());
        }

        return result;
    }

}
