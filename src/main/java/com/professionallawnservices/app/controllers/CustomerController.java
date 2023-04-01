package com.professionallawnservices.app.controllers;

/*
The CustomerController houses all the customer endpoints. Communication from the CustomerController
to the CustomerService is accomplished primarily through the CustomerRequest. Model attributes utilized in forms
should be of the request type (i.e. CustomerRequest) and not of data models, unless necessary. Objects exchanged between
endpoints should be of the data model type and not the request type, unless request form data is being sent.
 */

import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.CustomerRequest;
import com.professionallawnservices.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;

import static com.professionallawnservices.app.enums.RolesEnum.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("")
    public String customersView(
            Pageable pageable,
            Model model
    )
    {

        Result result = customerService.getAllCustomersPageable(pageable);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Customer> customers = (ArrayList<Customer>) result.getData();

        if (customers.size() == 0 && pageable.getPageNumber() != 0) {
            int previousPageIndex = pageable.getPageNumber() - 1;
            return "redirect:/customers?page=" + previousPageIndex;
        }

        model.addAttribute("customers", customers);
        model.addAttribute("searchSelect", "SEARCH");
        model.addAttribute("pageNumber", pageable.getPageNumber());

        return "customers";
    }

    @GetMapping("/add")
    public String addCustomerView(Model model) {

        CustomerRequest customerRequest = new CustomerRequest();
        model.addAttribute("customerRequest", customerRequest);
        model.addAttribute("addUpdate", "ADD");

        return "alter-customer";
    }

    @PostMapping("/add")
    public String addCustomer(
            @Valid @ModelAttribute("customerRequest") CustomerRequest customerRequest,
            BindingResult bindingResult
    )
    {

        if (bindingResult.hasErrors()) {
            throw new PlsRequestException("Request must contain a non negative integer value for frequency.");
        }

        if(
                ValidationHelpers.isNull(customerRequest)
                        || ValidationHelpers.isNullOrBlank(customerRequest.getName())
        )
        {
            throw new PlsRequestException("Request must contain name");
        }

        Result result = customerService.createCustomer(customerRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/customers/add";
    }

    @GetMapping("/update/{id}")
    public String updateCustomerView(
            @PathVariable("id") long id,
            Model model
    )
    {

        Result result = customerService.getCustomerById(new CustomerRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Customer customer = (Customer) result.getData();
        CustomerRequest customerRequest = new CustomerRequest(customer) ;


        model.addAttribute("customer", customer);
        model.addAttribute("customerRequest", customerRequest);
        model.addAttribute("addUpdate", "UPDATE");


        return "alter-customer";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute("customerRequest") CustomerRequest customerRequest,
            Model model
    )
    {
        if(
                ValidationHelpers.isNull(customerRequest)
                        || ValidationHelpers.isNullOrBlank(customerRequest.getName())
        )
        {
            throw new PlsRequestException("Request must contain name");
        }

        customerRequest.setId(id);

        Result result = customerService.updateCustomer(customerRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/customers/update/" + id;
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(
            @PathVariable(value = "id",required = true) long id,
            Model model
    )
    {

        Result result = customerService.deleteCustomerById(new CustomerRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("");
    }

}
