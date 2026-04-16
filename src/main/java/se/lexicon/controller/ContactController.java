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
                ContactView.displayMenu();
                String choice = contactView.getUserInput("Choice an option");


                switch (choice) {
                    case "1":
                        List<Contact> contacts = contactDAO.findAll();
                        break;
                    case "2":
                        List<Contact> contacts1 = contactDAO.save(contacts);
                        break;
                    case "3":
                        List<Contact> contacts2 = contactDAO.findByName(name);
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

    private void ListContacts() throws ContactStorageExeption {
        List<Contact> contacts = contactDAO.findAll();
        contactView.displayContacts(contacts);
    }

    private void createContact(Contact contact) throws ContactStorageExeption {
        String name = contactView.getUserInput("Enter Name:");
        String phone = contactView.getUserInput("Enter Phone Number:");

        Contact newContact = new Contact(name, phone);
        contactDAO.save(newContact);

        ContactView.displayMessage("Contact saved!");

    }

    private void findContact() throws ContactStorageExeption {
        String name = contactView.getUserInput("Enter Name:");
        Contact contact = contactDAO.findByName(name);

        if (contact != null) {
            contactView.displayMessage("Contact found!" + contact.getName() + "-" + contact.getPhoneNumber());
        } else {
            contactView.displayMessage("Contact not found!");
        }
    }

}
