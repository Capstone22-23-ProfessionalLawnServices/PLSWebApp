package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.services.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/help")
public class HelpController {

    @Autowired
    HelpService helpService;

    @PostMapping("/delete")
    public ResponseEntity<String> deleteHelp(
            @RequestParam(value = "jobId",required = true) long jobId,
            @RequestParam(value = "workerId", required = true) Long workerId,
            Model model
    )
    {

        Result result = helpService.deleteHelpByWorkerRequestAndJobRequest(
                new WorkerRequest(workerId),
                new JobRequest(jobId)
        );

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("/update-appointment/" + jobId);
    }
}