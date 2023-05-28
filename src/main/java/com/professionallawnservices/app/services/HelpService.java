package com.professionallawnservices.app.services;

import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Worker;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.HelpRequest;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.repos.WorkerRepo;
import com.professionallawnservices.app.repos.HelpRepo;
import com.professionallawnservices.app.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HelpService {

    @Autowired
    HelpRepo helpRepo;

    @Autowired
    WorkerRepo workerRepo;

    @Autowired
    JobRepo jobRepo;

    public Result deleteHelpByWorkerRequestAndJobRequest(WorkerRequest workerRequest, JobRequest jobRequest) {

        Result result = new Result();

        try{

            Worker worker = workerRepo.findById(workerRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid worker Id:" + workerRequest.getId()));
            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobRequest.getId()));

            ArrayList<Help> helpArrayList = helpRepo.getHelpByWorkerIdAndJobId(
                    worker.getWorkerId(),
                    job.getJobId()
            );

            helpRepo.deleteAll(helpArrayList);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting help with workerId: " +
                    workerRequest.getId() + " and jobId: " + jobRequest.getId());
        }

        return result;
    }

    public Result updateHelpWorkerPay(HelpRequest helpRequest) {

        Result result = new Result();

        try{

            Help help = helpRepo.findById(helpRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid worker Id:" + helpRequest.getId()));

            help.setWorkerPay(helpRequest.getWorkerPay());

            helpRepo.save(help);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue updating the help object with id: " + helpRequest.getId());
        }

        return result;
    }

}
