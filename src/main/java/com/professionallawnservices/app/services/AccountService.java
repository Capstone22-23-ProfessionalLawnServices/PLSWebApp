package com.professionallawnservices.app.services;

import com.professionallawnservices.app.enums.RolesEnum;
import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.request.UserRequest;
import com.professionallawnservices.app.models.request.WorkerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
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

            var newPassword = passwordEncoder.encode(userRequest.getNewPassword());

            jdbcUserDetailsManager.createUser(org.springframework.security.core.userdetails.User
                    .withUsername(userRequest.getUsername())
                    .password(newPassword)
                    .authorities(userRequest.getRole()).build());

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an error creating the account with username: " + userRequest.getUsername());
        }

        return result;
    }

    public Result updateAccount(UserRequest userRequest) {

        Result result = new Result();

        try {

            jdbcUserDetailsManager.changePassword(userRequest.getPassword(),
                    passwordEncoder.encode(userRequest.getNewPassword()));

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an error updating the account with username: " + userRequest.getUsername());
        }

        return result;
    }

    public Result deleteAccount(UserRequest userRequest) {

        Result result = new Result();

        try {

            jdbcUserDetailsManager.deleteUser(userRequest.getUsername());

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an error deleting the account with username: " + userRequest.getUsername());
        }

        return result;
    }

    public Result accountView() {

        Result result = new Result();

        try {

            Authentication systemUser = SecurityHelpers.getUser();
            UserRequest userRequest = new UserRequest(
                    systemUser.getName(),
                    systemUser.getAuthorities().toString(),
                    SecurityHelpers.getPrimaryUserRole()
            );

            result.setData(userRequest);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue executing accountView()");
        }

        return result;

    }
}
