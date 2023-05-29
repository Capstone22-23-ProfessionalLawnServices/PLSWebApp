package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Worker;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.HelpRequest;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.models.request.CustomerRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.services.WorkerService;
import com.professionallawnservices.app.services.CustomerService;
import com.professionallawnservices.app.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.professionallawnservices.app.enums.RolesEnum.EMPLOYEE;
import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
@RequestMapping("/appointments")
//@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_OWNER','ROLE_ADMIN')")
public class JobController {

    @Autowired
    JobService jobService;

    @Autowired
    CustomerService customerService;

    @Autowired
    WorkerService workerService;

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
            @RequestParam(value = "cost", required = true) double cost,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "scheduledDate", required = false) String scheduledDate,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "notes", required = false) String notes,
            Model model
    )
    {

        JobRequest jobRequest = new JobRequest();
        jobRequest.setCost(cost);
        jobRequest.setLocation(location);
        jobRequest.setScheduledDate(scheduledDate);
        jobRequest.setStartTime(startTime);
        jobRequest.setEndTime(endTime);
        jobRequest.setNotes(notes);

        Result result = jobService.createJob(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Job job = (Job) result.getData();

        return ResponseEntity.ok("" + job.getJobId());
    }

    @GetMapping("/update/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_OWNER','ROLE_ADMIN')")
    public String updateJobView(
            @PathVariable(value = "id", required = true) long id,
            Model model
    )
    {
        Result result = jobService.getJobById(new JobRequest(id));
        RolesEnum user = SecurityHelpers.getPrimaryUserRole();

        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Job job = (Job) result.getData();
        JobRequest jobRequest = new JobRequest(job);
        ArrayList<Help> helpArrayList = (ArrayList<Help>) jobService.getHelpByJobId(new JobRequest(id)).getData();

        model.addAttribute("job", job);
        model.addAttribute("jobRequest", jobRequest);
        model.addAttribute("helpArrayList", helpArrayList);
        model.addAttribute("addUpdate", "UPDATE");
        model.addAttribute("userAccessLevel", user.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);
        model.addAttribute("employeeAccessLevel", EMPLOYEE.accessLevel);


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
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam(value = "workerPay", required = false) Double workerPay,
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
        jobRequest.setNotes(notes);

        HelpRequest helpRequest = new HelpRequest();
        helpRequest.setWorkerPay(workerPay);


        Result result = jobService.updateJobPrimitiveFields(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully updated");
    }

    @GetMapping("/update/{id}/select-worker")
    public String updateAppointmentSelectWorkerView(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute("job") Job job,
            Model model
    )
    {

        Result result = workerService.getAllWorkers();

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Worker> workers = (ArrayList<Worker>) result.getData();

        model.addAttribute("selectSearch", "SELECT");
        model.addAttribute("workers", workers);
        model.addAttribute("jobId", id);
        model.addAttribute("pageNumber", 0);

        return "workers";
    }

    @PostMapping("/update/{id}/select-worker")
    public ResponseEntity<String> updateAppointmentAddWorker(
            @PathVariable(value = "id",required = true) long id,
            @RequestParam(value = "workerId", required = true) Long workerId,
            Model model
    )
    {

        Result result = new Result();
        JobRequest jobRequest = new JobRequest(id);

        if(workerId != null) {
            result = jobService.createHelp(new WorkerRequest(workerId), jobRequest);
        }

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully added worker");
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
        model.addAttribute("pageNumber", 0);

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

        Job job = (Job) result.getData();

        return ResponseEntity.ok(job.getCustomer().getCustomerName());
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
            @RequestParam(value = "workerId", required = true) Long workerId
    )
    {
        WorkerRequest workerRequest = new WorkerRequest(workerId);
        JobRequest jobRequest = new JobRequest(id);

        Result result = jobService.deleteHelpWithJobAndWorkerIds(jobRequest, workerRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully deleted help");
    }

}