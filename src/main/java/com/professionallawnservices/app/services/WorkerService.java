package com.professionallawnservices.app.services;

import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.data.Worker;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.repos.WorkerRepo;
import com.professionallawnservices.app.repos.HelpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.professionallawnservices.app.models.*;

import java.util.ArrayList;

@Service
public class WorkerService {

    @Autowired
    WorkerRepo workerRepo;

    @Autowired
    HelpRepo helpRepo;

    public Result getAllWorkers() {

        Result result = new Result();

        try{

            result.setData(workerRepo.findAll());

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue retrieving workers.");
        }

        return result;
    }

    public Result getAllWorkersPageable(Pageable pageable) {

        Result result = new Result();

        try{
            Page<Worker> workerPage = workerRepo.findAll(pageable);
            ArrayList<Worker> workerArrayList = new ArrayList<Worker>(workerPage.getContent());

            result.setData(workerArrayList);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue retrieving workers.");
        }

        return result;
    }

    public Result createWorker(WorkerRequest workerRequest) {

        Result result = new Result();

        try {

            Worker worker = new Worker(workerRequest);

            workerRepo.save(worker);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue saving the worker with name: " + workerRequest.getName());
        }

        return result;
    }

    public Result getWorkerById(WorkerRequest workerRequest) {

        Result result = new Result();

        try {

            Worker worker = workerRepo.findById(workerRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid worker Id:" + workerRequest.getId()));

            result.setData(worker);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding the worker with id: " + workerRequest.getId());
        }

        return result;
    }

    public Result updateWorker(WorkerRequest workerRequest) {

        Result result = new Result();

        try {

            Worker worker = new Worker(workerRequest);

            workerRepo.save(worker);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue updating the worker with id: " + workerRequest.getId());
        }

        return result;
    }

    public Result deleteWorkerById(WorkerRequest workerRequest) {

        Result result = new Result();

        try {

            Worker worker = workerRepo.getReferenceById(workerRequest.getId());

            ArrayList<Help> helpArrayList = helpRepo.getAllHelpByWorkerId(worker.getWorkerId());

            helpRepo.deleteAll(helpArrayList);
            workerRepo.delete(worker);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting the worker with id: " + workerRequest.getId());
        }

        return result;
    }

    public Result searchWorkers(WorkerRequest workerRequest) {

        Result result = new Result();

        try {

            if(workerRequest.getId() != -1) {
                result.setData(workerRepo.findById(workerRequest.getId()));
            }
            else if(!ValidationHelpers.isNullOrBlank(workerRequest.getName())) {
                result.setData(workerRepo.findByWorkerName(workerRequest.getName()));
            }
            else if(!ValidationHelpers.isNullOrBlank(workerRequest.getEmail())){
                //result.setData(contactRepo.findByContactEmail(contactRequest.getName()));
            }
            else if(!ValidationHelpers.isNullOrBlank(workerRequest.getPhone())) {
                //result.setData(contactRepo.findByContactPhone(contactRequest.getName()));
            }

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue searching for the worker.");
        }

        return result;
    }

}
