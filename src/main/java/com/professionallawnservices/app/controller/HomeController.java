package com.professionallawnservices.app.controller;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.enums.RolesEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

import static com.professionallawnservices.app.enums.RolesEnum.*;

@Controller
public class HomeController {

    @GetMapping(value = {"/home", "/"})
    //@PreAuthorize("hasRole('MANAGER')")
    public String viewHome(Model model) {
        RolesEnum user = SecurityHelpers.getPrimaryUserRole();

        model.addAttribute("userAccessLevel", user.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);

        return "home";
    }

}