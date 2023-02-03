package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.request.UserRequest;
import com.professionallawnservices.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public String accountView(Model model) {
        RolesEnum userRole = SecurityHelpers.getPrimaryUserRole();
        var systemUser = SecurityHelpers.getUser();
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(systemUser.getName());
        userRequest.setRole(systemUser.getAuthorities().toString());

        model.addAttribute("userAccessLevel", userRole.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);
        model.addAttribute("user", userRequest);

        return "account";
    }

    @GetMapping("/add-account")
    public String addAccountView(Model model) {
        model.addAttribute("user", new UserRequest());
        return "add-account";
    }

    @PostMapping("/create-account")
    public String createAccount(@ModelAttribute UserRequest userRequest) {
        if(
                ValidationHelpers.isNull(userRequest)
                || ValidationHelpers.isNullOrBlank(userRequest.getUsername())
                || ValidationHelpers.isNullOrBlank(userRequest.getNewPassword())
                || ValidationHelpers.isNullOrBlank(userRequest.getRole())
        )
        {
            throw new PlsRequestException("Request must contain username, new password, and role");
        }

        Result result = accountService.createAccount(userRequest);

        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/add-contact";
    }


    @PostMapping("/update-account")
    public String updateAccount(@ModelAttribute @Valid UserRequest userRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new PlsRequestException("Request must contain a new password with at least one capital, lowercase, " +
                    "and one number.");
        }

        if(
                ValidationHelpers.isNull(userRequest)
                        || ValidationHelpers.isNullOrBlank(userRequest.getUsername())
                        || ValidationHelpers.isNullOrBlank(userRequest.getNewPassword())
        )
        {
            throw new PlsRequestException("Request must contain username and new password");
        }

        Result result = accountService.updateAccount(userRequest);

        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/account";
    }

    @PostMapping("/rest/create-account")
    public ResponseEntity<String> createAccountRest(@RequestBody @Valid UserRequest userRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors().toString());
        }

        if(
                ValidationHelpers.isNull(userRequest)
                        || ValidationHelpers.isNullOrBlank(userRequest.getUsername())
                        || ValidationHelpers.isNullOrBlank(userRequest.getNewPassword())
                        || ValidationHelpers.isNullOrBlank(userRequest.getRole())
        )
        {
            throw new PlsRequestException("Request must contain username, new password, and role");
        }

        Result result = accountService.createAccount(userRequest);

        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Account successfully created");
    }

    @PostMapping("/rest/update-account")
    public ResponseEntity<String> updateAccountRest(@RequestBody UserRequest userRequest) {
        if(
                ValidationHelpers.isNull(userRequest)
                        || ValidationHelpers.isNullOrBlank(userRequest.getUsername())
                        || ValidationHelpers.isNullOrBlank(userRequest.getNewPassword())
        )
        {
            throw new PlsRequestException("Request must contain username and new password");
        }

        Result result = accountService.updateAccount(userRequest);

        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Account successfully updated");
    }

    @PostMapping("/rest/delete-account")
    public ResponseEntity<String> deleteAccountRest(@RequestBody UserRequest userRequest) {
        if(
                ValidationHelpers.isNull(userRequest)
                        || ValidationHelpers.isNullOrBlank(userRequest.getUsername())
        )
        {
            throw new PlsRequestException("Request must contain username");
        }

        Result result = accountService.deleteAccount(userRequest);

        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Account successfully deleted");
    }

}