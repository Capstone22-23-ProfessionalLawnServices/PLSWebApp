package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.request.UserRequest;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.services.AccountService;
import com.professionallawnservices.app.services.WorkerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/worker-account")
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_OWNER','ROLE_ADMIN')")
public class WorkerAccountController {

    @Autowired
    private WorkerAccountService workerAccountService;

    @GetMapping("/create-account/{id}")
    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_ADMIN')")
    public String createWorkerAccount(
            @PathVariable(value = "id",required = true) long id,
            Model model
    )
    {
        UserRequest userRequest = new UserRequest();

        model.addAttribute("addUpdate", "ADD");
        model.addAttribute("userRequest", userRequest);

        return "alter-account";
    }

    @PostMapping("/create-account/{id}")
    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_ADMIN')")
    public ResponseEntity<String> createWorkerAccount(
            @PathVariable(value = "id",required = true) long id,
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            Model model
    )
    {

        if (username.length() < 5 && password.length() < 5) {
            throw new PlsRequestException("Username and password must be longer than 4 characters");
        }

        WorkerRequest workerRequest = new WorkerRequest(id);
        UserRequest userRequest = new UserRequest();

        userRequest.setUsername(username);
        userRequest.setPassword(password);

        Result result = workerAccountService.createWorkerAccount(workerRequest, userRequest);

        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully added account");
    }

}
