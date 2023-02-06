package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.repos.CustomerRepo;
import com.professionallawnservices.app.repos.JobRepo;
import com.professionallawnservices.app.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class JobController {

    @Autowired
    JobService jobService;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("/appointments")
    public String jobsView(Model model) {

        Result result = jobService.getAllJobs();

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Job> jobs = (ArrayList<Job>) result.getData();

        model.addAttribute("jobs", jobs);
        //model.addAttribute("job", new JobRequest());
        return "appointments";
    }

    @GetMapping("/add-appointment")
    public String addJobView(Model model) {
        model.addAttribute("job", new JobRequest());

        return "add-appointment";
    }

    @GetMapping("/update-appointment/{id}")
    public String updateContactView(@PathVariable(value = "id", required = true) long id, Model model) {

        Result result = jobService.getJobById(new JobRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        JobRequest job = (JobRequest) result.getData();

        model.addAttribute("job", job);
        model.addAttribute("id", job.getId());
        return "update-appointment";
    }

    @PostMapping("/create-appointment")
    public String createJob(@ModelAttribute JobRequest jobRequest) {

        if(ValidationHelpers.isNull(jobRequest)) {
            throw new PlsRequestException("Request must contain customer id");
        }

        Result result = jobService.createJob(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/add-appointment";
    }

    @PostMapping("/update-appointment/{id}")
    public String updateJob(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute JobRequest jobRequest,
            Model model
    )
    {
        if(ValidationHelpers.isNull(jobRequest)) {
            throw new PlsRequestException("Request must contain name");
        }

        jobRequest.setId(id);

        Result result = jobService.updateJob(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/update-appointment/" + id;
    }

    @GetMapping("/delete-appointment/{id}")
    public String deleteJob(@PathVariable(value = "id", required = true) long id) {

        Result result = jobService.deleteJob(new JobRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/appointments";
    }
}