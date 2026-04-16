package se.lexicon.data;

import se.lexicon.model.Contact;
import java.util.List;

// Interface defines contract for Contact data access
public interface ContactDAO {

    // Abstract method - persist new contact
    void save(Contact contact);

    // Abstract method - retrieve all contacts from storage
    List<Contact> findAll();

    // Abstract method - search for specific contact by name,
    Contact findByName(String name);
}
