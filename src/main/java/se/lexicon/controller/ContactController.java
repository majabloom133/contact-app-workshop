package se.lexicon.controller;

import se.lexicon.data.ContactDAO;
import se.lexicon.exception.ExceptionHandler;
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
                ContactView.displayMenu();
                String choice = contactView.getUserInput("Choice an option");


                switch (choice) {
                    case "1":
                        addContact();
                        break;
                    case "2":
                        viewAllContacts();
                        break;
                    case "3":
                        searchContact();
                        break;
                    case "4":
                        running = false;
                        break;
                    default:
                        contactView.displayError("Invalid choice!");
                }


            } catch (Exception e) {
                String message = ExceptionHandler.handle(e);
                contactView.displayError(message);
            }
        }
    }

    private void addContact() throws Exception {
        String name = contactView.getUserInput("Enter name: ");
        String phone = contactView.getUserInput("Enter phone: ");

        Contact contact = new Contact(name, phone);
        contactDAO.save(contact);

        contactView.displayMessage("Contact added successfully!");
    }

    private void viewAllContacts() throws Exception {
        List<Contact> contacts = contactDAO.findAll();
        contactView.displayContacts(contacts);
    }

    private void searchContact() throws Exception {
        String name = contactView.getUserInput("Enter name to search: ");
        Contact contact = contactDAO.findByName(name);

        if (contact != null) {
            contactView.displayMessage(contact.toString());
        } else {
            contactView.displayError("Contact not found");
        }
    }

}
