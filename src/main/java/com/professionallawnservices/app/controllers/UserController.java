package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.repos.ContactsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;

import static com.professionallawnservices.app.enums.RolesEnum.*;


@Controller
public class UserController {

    @Autowired
    DataSource dataSource;

    @Autowired
    PasswordEncoder passwordEncoder;

/*
    @GetMapping Mapping("/addUser")
    //@PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public void addUser(@RequestParam(name="username", required = false, defaultValue="defaultUserName")String username,
                        @RequestParam(name = "password", required = false, defaultValue = "defaultPassword")String password) {

        var userRepo = new JdbcUserDetailsManager(dataSource);

        userRepo.createUser(User.withUsername("1").password(passwordEncoder.encode("1"))
                .authorities("ROLE_MANAGER", "ROLE_USER").build());

    }

 */

}