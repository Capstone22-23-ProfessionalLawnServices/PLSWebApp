package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.json.openweather.PlsWeather;
import com.professionallawnservices.app.models.request.CustomerRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.professionallawnservices.app.enums.RolesEnum.*;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @RequestMapping(method = RequestMethod.GET)
    public String viewHome(Model model) {
        RolesEnum user = SecurityHelpers.getPrimaryUserRole();
        ArrayList<PlsWeather> plsWeatherArrayList = homeService.getCalendarWeather();
        ArrayList<ArrayList<Job>> calendarJobs = (ArrayList<ArrayList<Job>>) homeService.getCalendarJobs().getData();
        ArrayList<Job> missedJobs = (ArrayList<Job>) homeService.getMissedJobs().getData();
        Job activeJob = (Job) homeService.getActiveJob().getData();

        model.addAttribute("userAccessLevel", user.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);
        model.addAttribute("weatherArrayList", plsWeatherArrayList);
        model.addAttribute("calendarJobs", calendarJobs);
        model.addAttribute("missedJobs", missedJobs);
        model.addAttribute("activeJob", activeJob);

        return "home";
    }

    @PostMapping("/reschedule")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_ADMIN')")
    public ResponseEntity<String> rescheduleJob(
            @RequestParam(value = "jobId",required = true) long jobId,
            @RequestParam(value = "scheduleDate", required = true) String scheduleDate
    )
    {

        Result result = new Result();
        JobRequest jobRequest = new JobRequest(jobId);
        jobRequest.setScheduledDate(scheduleDate);
        result = homeService.rescheduleJob(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully rescheduled job");
    }

    @PostMapping("/start-session")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_ADMIN')")
    public ResponseEntity<String> startJobSession(
            @RequestParam(value = "jobId",required = true) long jobId
    )
    {

        Result result = new Result();
        JobRequest jobRequest = new JobRequest(jobId);

        result = homeService.startSession(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully started job");
    }

    @PostMapping("/end-session")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_ADMIN')")
    public ResponseEntity<String> endJobSession(
            @RequestParam(value = "jobId",required = true) long jobId
    )
    {

        Result result = new Result();
        JobRequest jobRequest = new JobRequest(jobId);

        result = homeService.endSession(jobRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully ended job");
    }

    @PostMapping("/search-customers")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_ADMIN')")
    public ResponseEntity<ArrayList<Customer>> endJobSession(
            @RequestParam(value = "customerName",required = true) String customerName
    )
    {

        Result result = new Result();
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName(customerName);

        result = homeService.searchCustomers(customerRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Customer> customerArrayList = (ArrayList<Customer>) result.getData();

        return ResponseEntity.ok(customerArrayList);
    }

}