package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.models.Contact;
import com.professionallawnservices.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.sql.DataSource;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class AccountController {

    @Autowired
    DataSource dataSource;

    private JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

    @GetMapping("/account")
    public String accountView(Model model) {
        RolesEnum user = SecurityHelpers.getPrimaryUserRole();

        model.addAttribute("userAccessLevel", user.accessLevel);
        model.addAttribute("managerAccessLevel", MANAGER.accessLevel);

        return "account";
    }

    @PostMapping("/create-account")
    public RedirectView createAccount(@ModelAttribute User user) {
        userDetailsManager.createUser(org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(SecurityHelpers.passwordEncoder().encode(user.getPassword()))
                .authorities((GrantedAuthority) user.getRolls()).build());
        return new RedirectView("/add-contact");
    }


}