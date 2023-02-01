package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Result createAccount(UserRequest userRequest) {

        Result result = new Result();

        try {

            jdbcUserDetailsManager.createUser(org.springframework.security.core.userdetails.User
                    .withUsername(userRequest.getUsername())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .authorities(userRequest.getRole()).build());

            result.complete = true;
        }
        catch (Exception e) {
            result.complete = false;
            result.errorMessage = "There was an error creating the account with username: " + userRequest.getUsername();
        }

        return result;
    }

    public Result updateAccount(UserRequest userRequest) {

        Result result = new Result();

        try{

            jdbcUserDetailsManager.changePassword(userRequest.getPassword(),
                    passwordEncoder.encode(userRequest.getNewPassword()));

            result.complete = true;
        }
        catch (Exception e) {
            result.complete = false;
            result.errorMessage = "There was an error updating the account with username: " + userRequest.getUsername();
        }

        return result;
    }

}
