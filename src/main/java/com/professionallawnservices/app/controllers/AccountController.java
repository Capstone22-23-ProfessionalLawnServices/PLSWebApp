package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class AccountController {

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/account")
    public String accountView(Model model) {
        RolesEnum userRole = SecurityHelpers.getPrimaryUserRole();
        var systemUser = SecurityHelpers.getUser();
        User user = new User();
        user.setUsername(systemUser.getName());
        user.setRoll(systemUser.getAuthorities().toString());

        model.addAttribute("userAccessLevel", userRole.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);
        model.addAttribute("user", user);

        return "account";
    }

    @GetMapping("/add-account")
    public String addAccountView(Model model) {
        model.addAttribute("user", new User());
        return "add-account";
    }

    @PostMapping("/create-account")
    public RedirectView createAccount(@ModelAttribute User user) {
        jdbcUserDetailsManager.createUser(org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .authorities(user.getRoll()).build());
        return new RedirectView("/add-contact");
    }


    @PostMapping("/update-account")
    public String updateAccount(@ModelAttribute User user, Model model) {
        jdbcUserDetailsManager.changePassword(user.getPassword(), passwordEncoder.encode(user.getNewPassword()));
        return "account";
    }



}