package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.CustomerRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.services.CustomerService;
import com.professionallawnservices.app.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class JobController {

    @Autowired
    JobService jobService;

    @Autowired
    CustomerService customerService;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("/appointments")
    public String jobsView(Model model) {

        Result result = jobService.getAllJobs();

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Job> jobs = (ArrayList<Job>) result.getData();

        model.addAttribute("jobs", jobs);
        return "appointments";
    }

    /*
    @GetMapping("/add-appointment")
    public String addJobView(
            @RequestParam(value="customer-id", required = false) Long customerId,
            @RequestParam(value="contact-ids", required = false) ArrayList<Long> contactIds,
            Model model
    )
    {
        Customer customer = (Customer) jobService.getCustomerByIdList(customerId).getData();
        ArrayList<Contact> contacts = (ArrayList<Contact>) jobService.getContactsByIdList(contactIds).getData();

        model.addAttribute("job", new JobRequest());
        model.addAttribute("customer", customer);
        model.addAttribute("contacts", contacts);
        model.addAttribute("addUpdate", "ADD");


        return "alter-appointment";
    }

     */

    @GetMapping("/add-appointment")
    public String addJobView(Model model) {
        JobRequest jobRequest = new JobRequest();

        model.addAttribute("jobRequest", jobRequest);
        model.addAttribute("addUpdate", "ADD");

        return "alter-appointment";
    }

    @PostMapping("/add-appointment")
    public String createJob(@ModelAttribute("jobRequest") JobRequest jobRequest) {

        if(ValidationHelpers.isNull(jobRequest)) {
            throw new PlsRequestException("Request must contain customer id");
        }

        Result result = jobService.createJob(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/add-appointment";
    }

    @PostMapping("/add-appointment/select-customer")
    public String addAppointmentSelectCustomer(
            @RequestParam(value = "cost", required = true) double cost,
            @RequestParam(value = "location", required = true) String location,
            @RequestParam(value = "scheduledDate", required = true) Date scheduledDate,
            @RequestParam(value = "startTime", required = true) String startTime,
            @RequestParam(value = "endTime", required = true) String endTime,
            RedirectAttributes redirectAttributes,
            Model model
    )
    {

        JobRequest jobRequest = new JobRequest();
        jobRequest.setCost(cost);
        jobRequest.setLocation(location);
        jobRequest.setScheduledDate(scheduledDate);
        jobRequest.setStartTime(startTime);
        jobRequest.setStartTime(endTime);

        if(ValidationHelpers.isNull(jobRequest)) {
            throw new PlsRequestException("Request must contain customer id");
        }

        Result result = jobService.createJob(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Job job = (Job) result.getData();

        redirectAttributes.addFlashAttribute("job", job);

        return "redirect:/update-appointment/" + job.getJobId() + "/select-customer";
    }


    @GetMapping("/update-appointment/{id}/select-customer")
    public String addAppointmentSelectCustomerView(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute("job") Job job,
            Model model
    )
    {

        model.addAttribute("job", job);
        model.addAttribute("searchSelect", "SELECT");


        return "customers";
    }

    @PostMapping("/update-appointment/{id}/select-customer")
    public String addAppointmentSelectCustomerView(
            @PathVariable(value = "id", required = true) long id,
            @RequestParam(value = "customerId", required = true) Long customerId,
            Model model
    )
    {

        Result result = jobService.getJobById(new JobRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Job job = (Job) result.getData();
        JobRequest jobRequest = new JobRequest(job);

        result = customerService.getCustomerById(new CustomerRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Customer customer = (Customer) result.getData();
        jobRequest.setCustomer(customer);

        jobService.updateJob(jobRequest);

        return "redirect:/update-appointment/" + id;
    }

    @GetMapping("/update-appointment/{id}")
    public String updateJobView(
            @PathVariable(value = "id", required = true) long id,
            @RequestParam(value = "contactId", required = false) Long contactId,
            @RequestParam(value = "customerId", required = false) Long customerId,
            Model model
    )
    {
        Result result = jobService.getJobById(new JobRequest(id));


        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Job job = (Job) result.getData();
        JobRequest jobRequest = new JobRequest(job);
        ArrayList<Help> contacts = (ArrayList<Help>) jobService.getHelpByJobId(new JobRequest(id)).getData();

        model.addAttribute("job", job);
        model.addAttribute("jobRequest", jobRequest);
        model.addAttribute("contacts", contacts);
        model.addAttribute("addUpdate", "UPDATE");

        return "alter-appointment";
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

    @PostMapping("/delete-appointment/{id}")
    public String deleteJob(@PathVariable(value = "id", required = true) long id) {

        Result result = jobService.deleteJob(new JobRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/appointments";
    }
}