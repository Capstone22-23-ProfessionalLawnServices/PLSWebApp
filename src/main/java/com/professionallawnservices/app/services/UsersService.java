package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Users;
import com.professionallawnservices.app.models.data.WorkerAccount;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.repos.JobRepo;
import com.professionallawnservices.app.repos.UsersRepo;
import com.professionallawnservices.app.repos.WorkerAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsersService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    WorkerAccountRepo workerAccountRepo;

    public Result getUserByWorkerId(WorkerRequest workerRequest) {

        Result result = new Result();

        try {

            WorkerAccount workerAccount = workerAccountRepo.findWorkerAccountByWorkerId(workerRequest.getId());

            Users users = usersRepo.findByUsername(workerAccount.getUsers().getUsername());

            result.setData(users);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding the user with worker id: " + workerRequest.getId());
        }

        return result;

    }

}
