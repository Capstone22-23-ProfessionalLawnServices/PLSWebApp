package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.json.openweather.PlsWeather;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
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

        model.addAttribute("userAccessLevel", user.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);
        model.addAttribute("weatherArrayList", plsWeatherArrayList);
        model.addAttribute("calendarJobs", calendarJobs);
        model.addAttribute("missedJobs", missedJobs);

        return "home";
    }

    @PostMapping("/reschedule")
    public ResponseEntity<String> updateAppointmentAddContact(
            @RequestParam(value = "jobId",required = true) long jobId,
            @RequestParam(value = "scheduleDate", required = true) String scheduleDate,
            Model model
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

}