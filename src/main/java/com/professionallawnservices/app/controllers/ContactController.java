package com.professionallawnservices.app.controllers;

import com.professionallawnservices.app.exceptions.PlsRequestException;
import com.professionallawnservices.app.exceptions.PlsServiceException;
import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.Result;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.repos.ContactRepo;
import com.professionallawnservices.app.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

import static com.professionallawnservices.app.enums.RolesEnum.MANAGER;

@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    private static final String managerRole = MANAGER.roleName;

    @GetMapping("/contacts")
    public String contactsView(Model model) {

        Result result = contactService.getAllContacts();

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        ArrayList<Contact> contacts = (ArrayList<Contact>) result.getData();

        model.addAttribute("contacts", contacts);
        return "contacts";
    }



    @GetMapping("/add-contact")
    public String addContactView(Model model) {
        model.addAttribute("contact", new ContactRequest());

        return "add-contact";
    }


    @GetMapping("/update-contact/{id}")
    public String updateContactView(@PathVariable(value = "id", required = true) long id, Model model) {

        Result result = contactService.getContactById(new ContactRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        Contact contact = (Contact) result.getData();

        model.addAttribute("contact", contact);
        return "update-contact";
    }


    @PostMapping("/create-contact")
    public RedirectView createContact(@ModelAttribute ContactRequest contactRequest) {
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

        return new RedirectView("/add-contact");
    }



    @PostMapping("/update-contact/{id}")
    public RedirectView updateContact(
            @PathVariable(value = "id",required = true) long id,
            @ModelAttribute ContactRequest contactRequest,
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

        return new RedirectView("/update-contact/" + id);
    }

    @GetMapping("/delete-contact/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable("id") long id) {

        Result result = contactService.deleteContact(new ContactRequest(id));

        if(!result.getComplete()) {
            throw new PlsServiceException(result.getErrorMessage());
        }

        return ResponseEntity.ok("Successfully deleted contact");
    }

    /*
    @GetMapping("/search-contacts")
    public ResponseEntity<List<Contact>> getEmployeeByName(@RequestParam(name = "search") String search) {
        List<Contact> contacts = contactRepo.findByContactName(search);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping("/search-contacts-post")
    public ResponseEntity<List<Contact>> getEmployeeByNamePost(@RequestBody Contact contact) {
        String contactName = contact.getContactName();
        List<Contact> contacts = contactRepo.findByContactName(contactName);
        return ResponseEntity.ok(contacts);
    }

     */

    @PostMapping("/rest/create-contact")
    public RedirectView createContactRest(@RequestBody ContactRequest contactRequest) {
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

        return new RedirectView("/add-contact");
    }

}