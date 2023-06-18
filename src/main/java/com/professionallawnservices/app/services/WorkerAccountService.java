package com.professionallawnservices.app.services;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.*;
import com.professionallawnservices.app.models.request.UserRequest;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.repos.HelpRepo;
import com.professionallawnservices.app.repos.UsersRepo;
import com.professionallawnservices.app.repos.WorkerAccountRepo;
import com.professionallawnservices.app.repos.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WorkerAccountService {

    @Autowired
    WorkerRepo workerRepo;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    WorkerAccountRepo workerAccountRepo;

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Result createWorkerAccount(WorkerRequest workerRequest, UserRequest userRequest) {

        Result result = new Result();

        try {

            WorkerAccount workerAccount = new WorkerAccount();

            Worker worker = workerRepo.findById(workerRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" +
                            workerRequest.getId()));

            Users user = new Users();
            user.setUsername(userRequest.getUsername());

            var newPassword = passwordEncoder.encode(userRequest.getPassword());

            jdbcUserDetailsManager.createUser(org.springframework.security.core.userdetails.User
                    .withUsername(userRequest.getUsername())
                    .password(newPassword)
                    .authorities("ROLE_EMPLOYEE").build());

            workerAccount.setUsers(user);
            workerAccount.setWorker(worker);

            workerAccountRepo.save(workerAccount);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue creating the worker account");
        }

        return result;
    }

}
