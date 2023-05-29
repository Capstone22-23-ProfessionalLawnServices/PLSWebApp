package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("isAnonymous()")
public class LoginController {

    @GetMapping(value = {"/","/login"})
    public String loginView() {

        if (SecurityHelpers.isAuthenticated()) {
            return "redirect:/home";
        }

        return "login";
    }

}