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
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Customer;
import com.professionallawnservices.app.models.data.Job;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/contacts")
    public String contactsView(Model model) {

        Result result = contactService.getAllContacts();

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Contact> contacts = (ArrayList<Contact>) result.getData();

        model.addAttribute("contacts", contacts);
        model.addAttribute("selectSearch", "SEARCH");

        return "contacts";
    }

    @GetMapping("/update-appointment/{id}/select-contact")
    public String addAppointmentSelectCustomerView(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute("job") Job job,
            Model model
    )
    {

        Result result = contactService.getAllContacts();

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Contact> contacts = (ArrayList<Contact>) result.getData();

        model.addAttribute("selectSearch", "SELECT");
        model.addAttribute("contacts", contacts);
        model.addAttribute("jobId", id);

        return "contacts";
    }

    @GetMapping("/add-contact")
    public String addContactView(Model model) {

        model.addAttribute("contactRequest", new ContactRequest());
        model.addAttribute("addUpdate", "ADD");


        return "alter-contact";
    }

    @PostMapping("/add-contact")
    public String addContact(@ModelAttribute("contactRequest") ContactRequest contactRequest) {
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

        return "redirect:/add-contact";
    }

    @GetMapping("/update-contact/{id}")
    public String updateContactView(
            @PathVariable(value = "id", required = true) long id,
            Model model
    )
    {

        Result result = contactService.getContactById(new ContactRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Contact contact = (Contact) result.getData();
        ContactRequest contactRequest = new ContactRequest(contact);

        model.addAttribute("contact", contact);
        model.addAttribute("contactRequest", contactRequest);
        model.addAttribute("id", contactRequest.getId());
        model.addAttribute("addUpdate", "UPDATE");

        return "alter-contact";
    }

    @PostMapping("/update-contact/{id}")
    public String updateContact(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute("contactRequest") ContactRequest contactRequest,
            Model model
    )
    {
        if(
                ValidationHelpers.isNull(contactRequest)
                        || ValidationHelpers.isNullOrBlank(contactRequest.getName())
        )
        {
            throw new PlsRequestException("Request must contain name");
        }

        contactRequest.setId(id);

        Result result = contactService.updateContact(contactRequest);

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/update-contact/" + id;
    }

    @PostMapping("/delete-contact/{id}")
    public ResponseEntity<String> deleteContact(
            @PathVariable(value = "id",required = true) long id,
            Model model
    )
    {

        Result result = contactService.deleteContactById(new ContactRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("/contacts");
    }

    /*
    @GetMapping("/delete-contact/{id}")
    public String deleteContact(@PathVariable(value = "id", required = true) long id) {

        Result result = contactService.deleteContact(new ContactRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return "redirect:/contacts";
    }

     */


    @GetMapping("/search-contacts")
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
        model.addAttribute("contact", new ContactRequest());

        return "contacts";
    }

    /*

    @PostMapping("/search-contacts-post")
    public ResponseEntity<List<Contact>> getEmployeeByNamePost(@RequestBody Contact contact) {
        String contactName = contact.getContactName();
        List<Contact> contacts = contactRepo.findByContactName(contactName);
        return ResponseEntity.ok(contacts);
    }

     */

    /*
    The rest methods are intended for use with a api application like Postman and not with webpages.
     */

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

        return "redirect:/add-contact";
    }

}