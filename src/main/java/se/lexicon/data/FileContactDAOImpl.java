package se.lexicon.data;

import se.lexicon.model.Contact;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileContactDAOImpl implements ContactDAO {

// Path object define storage file location
    private Path filePath;

    // Constructor - Initialize file path (+ create file if it don't exist.)

    public FileContactDAOImpl(String fileName) {
        this.filePath = Paths.get(fileName);

        // Ensure file exists on system - prevent runtime errors
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                // Wrap standard IOException in custom exception
                throw new ContactStorageException("Critical error: Couldn't initialize storage file!", e);
            }
        }
    }

    @Override
    public void save(Contact contact) {
        // Business rule: Prevent duplicate entries - check if name already exists.
        if (findByName(contact.getName()) != null) {
            throw new DuplicateContactException("A contact with following name already exists: " + contact.getName());
        }

        // Open file in APPEND mode - add new data without deleting existing records.
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            // Serialization - convert contact object to CSV-formatted String
            writer.write(contact.getName() + "," + contact.getPhoneNumber());
            writer.newLine();
        } catch (IOException e) {
            throw new ContactStorageException("Failed to write to file!", e);
        }
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();

        // Try-with-resources - ensure BufferedReader is closed
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parsing - split line to Name and Phone number
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contacts.add(new Contact(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            throw new ContactStorageException("Error reading data from file!", e);
        }
        return contacts;
    }

    @Override
    public Contact findByName(String name) {
        // Linear search: Iterating through the list - find a case-insensitive match.
        return findAll().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

