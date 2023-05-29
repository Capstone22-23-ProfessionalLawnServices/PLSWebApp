package com.professionallawnservices.app.services;

import com.professionallawnservices.app.helpers.DateHelper;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Worker;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.models.request.CustomerRequest;
import com.professionallawnservices.app.models.request.JobRequest;
import com.professionallawnservices.app.repos.WorkerRepo;
import com.professionallawnservices.app.repos.CustomerRepo;
import com.professionallawnservices.app.repos.HelpRepo;
import com.professionallawnservices.app.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class JobService {

    @Autowired
    JobRepo jobRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    WorkerRepo workerRepo;

    @Autowired
    HelpRepo helpRepo;

    public Result getAllJobsPageable(Pageable pageable) {

        Result result = new Result();

        try {

            Page<Job> pageableJobs = jobRepo.findAll(pageable);
            ArrayList<Job> jobArrayList = new ArrayList<Job>(pageableJobs.getContent());

            result.setData(jobArrayList);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue retrieving jobs.");
        }

        return result;
    }

    public Result getJobById(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobRequest.getId()));
            ArrayList<Help> helpArrayList = helpRepo.getAllHelpByJobId(job.getJobId());

            result.setData(job);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding the job with id: " + jobRequest.getId());
        }

        return result;
    }

    public Result getHelpByJobId(JobRequest jobRequest) {

        Result result = new Result();

        try {

            ArrayList<Help> helpArrayList = helpRepo.getAllHelpByJobId(jobRequest.getId());

            result.setData(helpArrayList);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding the job with id: " + jobRequest.getId());
        }

        return result;

    }

    public Result createJob(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Job job = new Job(jobRequest);

            if (jobRequest.getCustomer() != null) {
                Customer customer = customerRepo.findById(jobRequest.getCustomer().getCustomerId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" +
                                jobRequest.getCustomer().getCustomerId()));
                job.setCustomer(customer);
            }

            jobRepo.save(job);

            result.setData(job);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue saving the job with customer id: " + jobRequest.getCustomer());
        }

        return result;
    }

    public Result updateJobPrimitiveFields(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Job jobFromRequest = new Job(jobRequest);
            Job job = (Job) getJobById(jobRequest).getData();

            job.setCost(jobFromRequest.getCost());
            job.setJobLocation(jobFromRequest.getJobLocation());
            job.setScheduledDate(jobFromRequest.getScheduledDate());
            job.setStartTime(jobFromRequest.getStartTime());
            job.setEndTime(jobFromRequest.getEndTime());
            job.setNotes(jobFromRequest.getNotes());

            if (job.getEndTime() != null && job.getCustomer() != null && job.getCustomer().getFrequency() > 0) {
                deleteUnfinishedFollowingJobs(job);
                automaticScheduleNextAppointment(job);
            }

            jobRepo.save(job);

            result.setData(job);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue updating the job with id: " + jobRequest.getId());
        }

        return result;
    }

    public Result deleteJob(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobRequest.getId()));
            ArrayList<Help> helpArrayList = helpRepo.getAllHelpByJobId(job.getJobId());

            helpRepo.deleteAll(helpArrayList);
            jobRepo.delete(job);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting the job with id: " + jobRequest.getId());
        }

        return result;
    }

    public Result getCustomerByIdList(Long customerId) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.findById(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + customerId));

            result.setData(customer);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue getting customer with id: " + customerId);
        }

        return result;
    }

    public Result createHelp(WorkerRequest workerRequest, JobRequest jobRequest) {

        Result result = new Result();

        try {

            Worker worker = workerRepo.findById(workerRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + workerRequest.getId()));
            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + jobRequest.getId()));

            Help help = new Help(worker, job);

            helpRepo.save(help);

            result.setData(help);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue creating help for job with id:" + jobRequest.getId());
        }

        return result;
    }

    public Result getWorkersByIdList(ArrayList<Long> workerIds) {

        Result result = new Result();

        try {

            List<Worker> workers = workerRepo.findAllById(workerIds);

            result.setData(workers);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue getting workers with ids: " + workerIds);
        }

        return result;
    }

    public Result getCustomerByJobRequest(JobRequest jobRequest) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.findById(jobRequest.getCustomer().getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + jobRequest.getCustomer()));

            result.setData(customer);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue getting customer with id: " + jobRequest.getCustomer());
        }

        return result;
    }

    public Result getWorkersByJobRequest(JobRequest jobRequest) {

        Result result = new Result();

        try {

            ArrayList<Long> workerIds = new ArrayList<Long>();

            for (Worker worker :
                    jobRequest.getWorkers()) {
                workerIds.add(worker.getWorkerId());
            }


            if (workerIds.size() == 0) {
                result.setComplete(true);
                return result;
            }

            List<Worker> workers = workerRepo.findAllById(workerIds);

            result.setData(workers);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue getting customers from job with id: " + jobRequest.getId());
        }

        return result;
    }

    public Result addCustomerToJobWithIds(JobRequest jobRequest, CustomerRequest customerRequest) {

        Result result = new Result();

        try {

            Customer customer = customerRepo.findById(customerRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + jobRequest.getCustomer()));

            Job job = jobRepo.findById(jobRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + jobRequest.getId()));

            job.setCustomer(customer);

            if (customer.getRegularCost() != 0) {
                job.setCost(customer.getRegularCost());
            }

            jobRepo.save(job);

            result.setData(job);
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue adding customer with id: " +
                    customerRequest.getId());
        }

        return result;
    }

    public Result deleteHelpWithJobAndWorkerIds(JobRequest jobRequest, WorkerRequest workerRequest) {

        Result result = new Result();

        try {

            ArrayList<Help> helpArrayList = helpRepo.getHelpByWorkerIdAndJobId(workerRequest.getId(), jobRequest.getId());

            ArrayList<Long> helpIds = new ArrayList<Long>();

            for (Help help :
                    helpArrayList) {
                helpIds.add(help.getId());
            }

            helpRepo.deleteAllById(helpIds);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting help with job id: " + jobRequest.getId() +
                    "and worker id: " + workerRequest.getId());
        }

        return result;
    }

    public void automaticScheduleNextAppointment(Job job) {

        try {

            Job newJob = new Job();
            Customer customer = customerRepo.findById(job.getCustomer().getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" +
                            job.getCustomer().getCustomerId())
                    );
            newJob.setJobId(0);
            newJob.setJobLocation(job.getJobLocation());
            newJob.setCustomer(job.getCustomer());
            newJob.setCost(customer.getRegularCost());
            newJob.setNotes("");
            newJob.setStartTime(null);
            newJob.setEndTime(null);
            newJob.setTotalTime(0.0);

            Date date = DateHelper.sqlDateAddDays(
                    job.getScheduledDate(),
                    newJob.getCustomer().getFrequency()
                    );

            newJob.setScheduledDate(date);

            jobRepo.save(newJob);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public void deleteUnfinishedFollowingJobs(Job job) {

        try {

            ArrayList<Job> jobArrayList = jobRepo.findByScheduledDateGreaterThan(job.getScheduledDate());

            jobRepo.deleteAll(jobArrayList);

        }
        catch (Exception e) {
            throw e;
        }
    }
}
