package se.lexicon.view;

import se.lexicon.model.Contact;

import java.util.List;
import java.util.Scanner;

public class ContactView {

    private final Scanner scanner = new Scanner(System.in);

    public static void displayMenu() {
        System.out.println("\n--- Contact App ---");
        System.out.println("1. Add Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Exit");
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void displayContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        contacts.forEach(System.out::println);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.err.println(message);
    }
}
