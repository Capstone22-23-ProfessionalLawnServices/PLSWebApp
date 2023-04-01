package com.professionallawnservices.app.controllers;

/*
The AccountController houses all the account endpoints. Communication from the AccountController
to the AccountService is accomplished primarily through the UserRequest. Model attributes utilized in forms
should be of the request type (i.e. UserRequest) and not of data models, unless necessary. Objects exchanged between
endpoints should be of the data model type and not the request type, unless request form data is being sent.
 */

import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.request.UserRequest;
import com.professionallawnservices.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public String accountView(Model model) {

        Result result = accountService.accountView();

        if (!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        UserRequest userRequest = (UserRequest) result.getData();

        model.addAttribute("userAccessLevel", userRequest.getRolesEnum().accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);
        model.addAttribute("userRequest", userRequest);
        model.addAttribute("addUpdate", "UPDATE");

        return "alter-account";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_OWNER','ROLE_ADMIN')")
    public String updateAccount(
            @ModelAttribute("userRequest") @Valid UserRequest userRequest,
            BindingResult bindingResult
    )
    {

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

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_ADMIN')")
    public String addAccountView(Model model) {

        UserRequest userRequest = new UserRequest();

        model.addAttribute("userRequest", userRequest);
        model.addAttribute("addUpdate", "ADD");

        return "alter-account";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_ADMIN')")
    public String addAccount(@ModelAttribute("userRequest") UserRequest userRequest) {
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

        return "redirect:/account/update";
    }

    /*
    The rest methods are intended for use with a api application like Postman and not with webpages.
     */

    /*
    @PostMapping("/rest/create-account")
    public ResponseEntity<String> createAccountRest(
            @RequestBody @Valid UserRequest userRequest,
            BindingResult bindingResult
    )
    {

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

     */
}