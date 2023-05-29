package com.professionallawnservices.app.controllers;

/*
The ContactController houses all the contact endpoints. Communication from the ContactController
to the ContactService is accomplished primarily through the ContactRequest. Model attributes utilized in forms
should be of the request type (i.e. ContactRequest) and not of data models, unless necessary. Objects exchanged between
endpoints should be of the data model type and not the request type, unless request form data is being sent.
 */

import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Worker;
import com.professionallawnservices.app.models.request.WorkerRequest;
import com.professionallawnservices.app.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/workers")
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_OWNER','ROLE_ADMIN')")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @GetMapping("")
    public String workersView(
            Pageable pageable,
            Model model
    )
    {

        Result result = workerService.getAllWorkersPageable(pageable);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Worker> workers = (ArrayList<Worker>) result.getData();

        if (workers.size() == 0 && pageable.getPageNumber() != 0) {
            int previousPageIndex = pageable.getPageNumber() - 1;
            return "redirect:/workers?page=" + previousPageIndex;
        }

        model.addAttribute("workers", workers);
        model.addAttribute("selectSearch", "SEARCH");
        model.addAttribute("pageNumber", pageable.getPageNumber());

        return "workers";
    }

    @GetMapping("/add")
    public String addWorkerView(Model model) {

        model.addAttribute("workerRequest", new WorkerRequest());
        model.addAttribute("addUpdate", "ADD");

        return "alter-worker";
    }

    @PostMapping("/add")
    public String addWorker(@ModelAttribute("workerRequest") WorkerRequest workerRequest) {
        if(
                ValidationHelpers.isNull(workerRequest)
                        || ValidationHelpers.isNullOrBlank(workerRequest.getName())
        )
        {
            throw new PlsRequestException("Request must contain name");
        }

        Result result = workerService.createWorker(workerRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/workers/add";
    }

    @GetMapping("/update/{id}")
    public String updateWorkerView(
            @PathVariable(value = "id", required = true) long id,
            Model model
    )
    {

        Result result = workerService.getWorkerById(new WorkerRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Worker worker = (Worker) result.getData();
        WorkerRequest workerRequest = new WorkerRequest(worker);

        model.addAttribute("worker", worker);
        model.addAttribute("workerRequest", workerRequest);
        model.addAttribute("id", workerRequest.getId());
        model.addAttribute("addUpdate", "UPDATE");

        return "alter-worker";
    }

    @PostMapping("/update/{id}")
    public String updateWorker(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute("workerRequest") WorkerRequest workerRequest,
            Model model
    )
    {
        if(
                ValidationHelpers.isNull(workerRequest)
                        || ValidationHelpers.isNullOrBlank(workerRequest.getName())
        )
        {
            throw new PlsRequestException("Request must contain name");
        }

        workerRequest.setId(id);

        Result result = workerService.updateWorker(workerRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/workers/update/" + id;
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorker(
            @PathVariable(value = "id",required = true) long id,
            Model model
    )
    {

        Result result = workerService.deleteWorkerById(new WorkerRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("");
    }

    /*
    @GetMapping("/search")
    public String getEmployeeByName(
            @RequestParam(value = "name", required = true) String name,
            Model model
    )
    {
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setName(name);

        Result result = contactService.searchContacts(contactRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Contact> contacts = (ArrayList<Contact>) result.getData();

        model.addAttribute("contacts", contacts);
        model.addAttribute("selectSearch", "SEARCH");
        model.addAttribute("pageNumber", "0");
        model.addAttribute("contact", new ContactRequest());

        return "contacts";
    }
    */

    /*
    The rest methods are intended for use with a api application like Postman and not with webpages.


    @PostMapping("/rest/create-contact")
    public String createContactRest(@RequestBody ContactRequest contactRequest) {
        if(
                ValidationHelpers.isNull(contactRequest)
                        || ValidationHelpers.isNullOrBlank(contactRequest.getName())
        )
        {
            throw new PlsRequestException("Request must contain name");
        }

        Result result = contactService.createContact(contactRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/contacts/add";
    }
         */

}