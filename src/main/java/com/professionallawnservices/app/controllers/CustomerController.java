package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.models.Customer;
import com.professionallawnservices.app.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.professionallawnservices.app.enums.RolesEnum;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static com.professionallawnservices.app.enums.RolesEnum.*;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepo customerRepo;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("/customers")
    public String customersView(Model model) {
        model.addAttribute("customers", customerRepo.findAll());
        return "customers";
    }

    @GetMapping("/add-customer")
    public String addCustomerView(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }

    @GetMapping("/update-customer/{id}")
    public String updateCustomerView(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "update-customer";
    }

    @PostMapping("/create-customer")
    public RedirectView createCustomer(@ModelAttribute Customer customer) {
        customerRepo.save(customer);
        return new RedirectView("/add-customer");
    }

    @PostMapping("/update-customer/{id}")
    public RedirectView updateCustomer(@PathVariable("id") long id, @ModelAttribute Customer customer,Model model) {
        customer.setCustomerId(id);
        customerRepo.save(customer);
        return new RedirectView("/update-customer/" + id);
    }

    /*
    public Customer getUser(@RequestParam(name="name", required=true, defaultValue="World") String name, Model model) {

    }

     */

}
