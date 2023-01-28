package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class AccountController {

    @GetMapping("/account")
    public String accountView(Model model) {
        RolesEnum user = SecurityHelpers.getPrimaryUserRole();

        model.addAttribute("userAccessLevel", user.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);

        return "account";
    }

}