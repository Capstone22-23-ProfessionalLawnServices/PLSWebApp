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
import com.professionallawnservices.app.services.HelpService;
import com.professionallawnservices.app.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
@RequestMapping("/help")
public class HelpController {

    @Autowired
    HelpService helpService;

    @PostMapping("/delete")
    public ResponseEntity<String> deleteHelp(
            @RequestParam(value = "jobId",required = true) long jobId,
            @RequestParam(value = "contactId", required = true) Long contactId,
            Model model
    )
    {

        Result result = helpService.deleteHelpByContactRequestAndJobRequest(
                new ContactRequest(contactId),
                new JobRequest(jobId)
        );

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("/update-appointment/" + jobId);
    }
}