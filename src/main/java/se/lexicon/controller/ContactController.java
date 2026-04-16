package se.lexicon.controller;

import se.lexicon.data.ContactDAO;
import se.lexicon.model.Contact;
import se.lexicon.view.ContactView;

import java.util.List;


public class ContactController {
    private ContactDAO contactDAO;
    private ContactView contactView;


    public ContactController(ContactDAO contactDAO, ContactView contactView) {
        this.contactDAO = contactDAO;
        this.contactView = contactView;
    }

    public void run() {
        boolean running = true;
        while (running) {
            try {


                contactView.displayMenu();

                String choice = contactView.getUserInput("Choice an option");


                switch (choice) {
                    case "1":
                        createContact();
                        break;
                    case "2":
                        ListContacts();
                        break;
                    case "3":
                        findContact();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        contactView.displayError("invalid choice");
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void ListContacts() throws Exception {
        List<Contact> contacts = contactDAO.findAll();
        contactView.displayContacts(contacts);
    }

    private void createContact() throws Exception {
        String name = contactView.getUserInput("Enter Name:");
        String phone = contactView.getUserInput("Enter Phone Number:");

        Contact newContact = new Contact(name, phone);
        contactDAO.save(newContact);

        contactView.displayMessage("Contact saved!");

    }

    private void findContact() throws Exception {
        String name = contactView.getUserInput("Enter Name:");
        Contact contact = contactDAO.findByName(name);

        if (contact != null) {
            contactView.displayMessage("Contact found!" + contact.getName() + "-" + contact.getPhoneNumber());
        } else {
            contactView.displayMessage("Contact not found!");
        }
    }

}
