package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.request.UserRequest;
import com.professionallawnservices.app.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class AccountController {

    @GetMapping("/account")
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
    public RedirectView createAccount(@ModelAttribute UserRequest userRequest, Model model) {
        if(
                ValidationHelpers.isNull(userRequest)
                || !ValidationHelpers.isNullOrBlank(userRequest.getUsername())
                || !ValidationHelpers.isNullOrBlank(userRequest.getNewPassword())
                || !ValidationHelpers.isNullOrBlank(userRequest.getRole())
        )
        {
            throw new PlsRequestException("Request must contain username, new password, and role");
        }

        AccountService accountService = new AccountService();

        Result result = accountService.createAccount(userRequest);

        if (!result.complete) {
            throw new PlsServiceException(result.errorMessage);
        }

        return new RedirectView("/add-contact");
    }


    @PostMapping("/update-account")
    public String updateAccount(@ModelAttribute UserRequest userRequest, Model model) {
        if(
                ValidationHelpers.isNull(userRequest)
                        || !ValidationHelpers.isNullOrBlank(userRequest.getUsername())
                        || !ValidationHelpers.isNullOrBlank(userRequest.getNewPassword())
        )
        {
            throw new PlsRequestException("Request must contain username and new password");
        }

        AccountService accountService = new AccountService();

        Result result = accountService.updateAccount(userRequest);

        if (!result.complete) {
            throw new PlsServiceException(result.errorMessage);
        }

        return "account";
    }



}