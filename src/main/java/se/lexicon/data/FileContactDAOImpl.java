package se.lexicon.data;

import se.lexicon.model.Contact;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileContactDAOImpl implements ContactDAO {

    private final Path filePath;

    public FileContactDAOImpl(String fileName) {
        this.filePath = Paths.get(fileName);
    }

    @Override
    public List<Contact> findAll() throws ContactStorageException {
        List<Contact> contacts = new ArrayList<>();

        try {
            if (!Files.exists(filePath)) {
                return contacts;
            }

            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                contacts.add(new Contact(parts[0], parts[1]));
            }

        } catch (IOException e) {
            throw new ContactStorageException("Failed to read contacts", e);
        }

        return contacts;
    }

    @Override
    public void save(Contact contact) throws ContactStorageException, DuplicateContactException {
        List<Contact> contacts = findAll();

        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(contact.getName())) {
                throw new DuplicateContactException("Contact already exists");
            }
        }

        try {
            String line = contact.getName() + "," + contact.getPhoneNumber();
            Files.write(filePath, (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new ContactStorageException("Failed to save contact", e);
        }
    }

    @Override
    public Contact findByName(String name) throws ContactStorageException {
        List<Contact> contacts = findAll();

        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }

        return null;
    }
}