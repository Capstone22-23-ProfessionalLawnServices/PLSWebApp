package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.helpers.SecurityHelpers;
import com.professionallawnservices.app.models.Customer;
import com.professionallawnservices.app.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.professionallawnservices.app.enums.RolesEnum;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.professionallawnservices.app.enums.RolesEnum.*;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepo customerRepo;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping(value = "/getCustomers")
    //@PreAuthorize("hasRole('MANAGER')")
    public List<Customer> getUsers(Model model) {
        return customerRepo.findAll();
    }

    /*
    public Customer getUser(@RequestParam(name="name", required=true, defaultValue="World") String name, Model model) {

    }

     */

}
