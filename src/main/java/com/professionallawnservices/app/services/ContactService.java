package com.professionallawnservices.app.services;

import com.professionallawnservices.app.helpers.ValidationHelpers;
import com.professionallawnservices.app.models.data.Contact;
import com.professionallawnservices.app.models.data.Help;
import com.professionallawnservices.app.models.request.ContactRequest;
import com.professionallawnservices.app.repos.ContactRepo;
import com.professionallawnservices.app.repos.HelpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.professionallawnservices.app.models.*;

import java.util.ArrayList;

@Service
public class ContactService {

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    HelpRepo helpRepo;

    public Result getAllContacts() {

        Result result = new Result();

        try{

            result.setData(contactRepo.findAll());

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue retrieving contacts.");
        }

        return result;
    }

    public Result createContact(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            Contact contact = new Contact(contactRequest);

            contactRepo.save(contact);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue saving the contact with name: " + contactRequest.getName());
        }

        return result;
    }

    public Result getContactById(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            Contact contact = contactRepo.findById(contactRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + contactRequest.getId()));

            result.setData(new ContactRequest(contact));
            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue finding the contact with id: " + contactRequest.getId());
        }

        return result;
    }

    public Result updateContact(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            Contact contact = new Contact(contactRequest);

            contactRepo.save(contact);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue updating the contact with id: " + contactRequest.getId());
        }

        return result;
    }

    public Result deleteContact(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            Contact contact = contactRepo.findById(contactRequest.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + contactRequest.getId()));
            ArrayList<Help> helpArrayList = helpRepo.getAllHelpByContactId(contact.getContactId());

            helpRepo.deleteAll(helpArrayList);
            contactRepo.delete(contact);

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue deleting the contact with id: " + contactRequest.getId());
        }

        return result;
    }

    public Result searchContacts(ContactRequest contactRequest) {

        Result result = new Result();

        try {

            if(contactRequest.getId() != -1) {
                result.setData(contactRepo.findById(contactRequest.getId()));
            }
            else if(!ValidationHelpers.isNullOrBlank(contactRequest.getName())) {
                result.setData(contactRepo.findByContactName(contactRequest.getName()));
            }
            else if(!ValidationHelpers.isNullOrBlank(contactRequest.getEmail())){
                //result.setData(contactRepo.findByContactEmail(contactRequest.getName()));
            }
            else if(!ValidationHelpers.isNullOrBlank(contactRequest.getPhone())) {
                //result.setData(contactRepo.findByContactPhone(contactRequest.getName()));
            }

            result.setComplete(true);
        }
        catch (Exception e) {
            result.setComplete(false);
            result.setErrorMessage("There was an issue searching for the contact.");
        }

        return result;
    }

}
