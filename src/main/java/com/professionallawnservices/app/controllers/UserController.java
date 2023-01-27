package com.professionallawnservices.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;


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