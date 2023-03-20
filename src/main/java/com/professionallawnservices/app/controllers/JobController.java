package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.models.request.CustomerRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.services.ContactService;
import com.professionallawnservices.app.services.CustomerService;
import com.professionallawnservices.app.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
@RequestMapping("/appointments")
public class JobController {

    @Autowired
    JobService jobService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ContactService contactService;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("")
    public String jobsView(
            Pageable pageable,
            Model model
    )
    {

        Result result = jobService.getAllJobsPageable(pageable);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Job> jobs = (ArrayList<Job>) result.getData();

        if (jobs.size() == 0 && pageable.getPageNumber() != 0) {
            int previousPageIndex = pageable.getPageNumber() - 1;
            return "redirect:/appointments?page=" + previousPageIndex;
        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("pageNumber", pageable.getPageNumber());
        return "appointments";
    }

    @GetMapping("/add")
    public String addJobView(Model model) {
        JobRequest jobRequest = new JobRequest();

        model.addAttribute("jobRequest", jobRequest);
        model.addAttribute("addUpdate", "ADD");

        return "alter-appointment";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addJob(
            @RequestParam(value = "cost", required = false) double cost,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "scheduledDate", required = false) String scheduledDate,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            Model model
    )
    {

        JobRequest jobRequest = new JobRequest();
        jobRequest.setCost(cost);
        jobRequest.setLocation(location);
        jobRequest.setScheduledDate(scheduledDate);
        jobRequest.setStartTime(startTime);
        jobRequest.setEndTime(endTime);

        if(ValidationHelpers.isNull(jobRequest)) {
            throw new PlsRequestException("Request must contain customer id");
        }

        Result result = jobService.createJob(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Job job = (Job) result.getData();

        return ResponseEntity.ok("" + job.getJobId());
    }

    @GetMapping("/update/{id}")
    public String updateJobView(
            @PathVariable(value = "id", required = true) long id,
            Model model
    )
    {
        Result result = jobService.getJobById(new JobRequest(id));


        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Job job = (Job) result.getData();
        JobRequest jobRequest = new JobRequest(job);
        ArrayList<Help> helpArrayList = (ArrayList<Help>) jobService.getHelpByJobId(new JobRequest(id)).getData();
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        for (Help help:
                helpArrayList) {
            contacts.add(help.getContact());
        }

        model.addAttribute("job", job);
        model.addAttribute("jobRequest", jobRequest);
        model.addAttribute("contacts", contacts);
        model.addAttribute("addUpdate", "UPDATE");

        return "alter-appointment";
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateJob(
            @PathVariable(value = "id", required = true) long id,
            @RequestParam(value = "cost", required = false) double cost,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "scheduledDate", required = false) String scheduledDate,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            Model model
    )
    {

        JobRequest jobRequest = new JobRequest();
        jobRequest.setId(id);
        jobRequest.setCost(cost);
        jobRequest.setLocation(location);
        jobRequest.setScheduledDate(scheduledDate);
        jobRequest.setStartTime(startTime);
        jobRequest.setEndTime(endTime);

        Result result = jobService.updateJobPrimitiveFields(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully updated");
    }

    @GetMapping("/update/{id}/select-contact")
    public String updateAppointmentSelectContactView(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute("job") Job job,
            Model model
    )
    {

        Result result = contactService.getAllContacts();

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Contact> contacts = (ArrayList<Contact>) result.getData();

        model.addAttribute("selectSearch", "SELECT");
        model.addAttribute("contacts", contacts);
        model.addAttribute("jobId", id);

        return "contacts";
    }

    @PostMapping("/update/{id}/select-contact")
    public ResponseEntity<String> updateAppointmentAddContact(
            @PathVariable(value = "id",required = true) long id,
            @RequestParam(value = "contactId", required = true) Long contactId,
            Model model
    )
    {

        Result result = new Result();
        JobRequest jobRequest = new JobRequest(id);

        if(contactId != null) {
            result = jobService.createHelp(new ContactRequest(contactId), jobRequest);
        }

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully added contact");
    }

    @GetMapping("/update/{id}/select-customer")
    public String addAppointmentSelectCustomerView(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute("job") Job job,
            Model model
    )
    {

        Result result = customerService.getAllCustomers();

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Customer> customers = (ArrayList<Customer>) result.getData();
        job.setJobId(id);

        model.addAttribute("customers", customers);
        model.addAttribute("job", job);
        model.addAttribute("searchSelect", "SELECT");

        return "customers";
    }

    @PostMapping("/update/{id}/select-customer")
    public ResponseEntity<String> updateAppointmentAddCustomer(
            @PathVariable(value = "id",required = true) long id,
            @RequestParam(value = "customerId", required = true) Long customerId,
            Model model
    )
    {

        CustomerRequest customerRequest = new CustomerRequest(customerId);
        JobRequest jobRequest = new JobRequest(id);

        Result result = jobService.addCustomerToJobWithIds(jobRequest, customerRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully added customer");
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable(value = "id", required = true) long id
    )
    {

        Result result = jobService.deleteJob(new JobRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("/appointments");
    }

    @PostMapping("/update/{id}/delete-help")
    public ResponseEntity<String> deleteHelp(
            @PathVariable(value = "id", required = true) long id,
            @RequestParam(value = "contactId", required = true) Long contactId
    )
    {
        ContactRequest contactRequest = new ContactRequest(contactId);
        JobRequest jobRequest = new JobRequest(id);

        Result result = jobService.deleteHelpWithJobAndContactIds(jobRequest, contactRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully deleted help");
    }
}