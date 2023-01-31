package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.repos.CustomerRepo;
import com.professionallawnservices.app.repos.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class JobController {

    @Autowired
    JobRepo jobRepo;

    @Autowired
    CustomerRepo customerRepo;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("/appointments")
    public String jobsView(Model model) {
        model.addAttribute("jobs", jobRepo.findAll());
        return "appointments";
    }

    @GetMapping("/add-appointment")
    public String addJobView(Model model) {
        model.addAttribute("job", new Job());
        return "add-appointment";
    }

    @GetMapping("/update-appointment/{id}")
    public String updateContactView(@PathVariable("id") long id, Model model) {
        Job job = jobRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
        model.addAttribute("contact", job);
        return "update-contact";
    }

    @PostMapping("/create-appointment")
    public RedirectView createJob(@ModelAttribute Job job) {
        job.setCustomer(customerRepo.getById(job.getCustomer().getCustomerId()));
        jobRepo.save(job);
        return new RedirectView("/add-appointment");
    }

    @PostMapping("/update-appointment/{id}")
    public RedirectView updateJob(@PathVariable("id") long id, @ModelAttribute Job job,Model model) {
        job.setJobId(id);
        jobRepo.save(job);
        return new RedirectView("/update-appointment/" + id);
    }

    @GetMapping("/delete-appointment/{id}")
    public ResponseEntity<Job> deleteJob(@PathVariable("id") long id) {
        Job job = jobRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));
        jobRepo.delete(job);
        return ResponseEntity.ok(job);
    }

    @PostMapping("/test")
    public RedirectView test(@ModelAttribute Job job) {
        Customer customer = customerRepo.findById(job.getCustomer().getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id"));
        job.setCustomer(customer);
        jobRepo.save(job);
        return new RedirectView("/add-appointment");
    }
}