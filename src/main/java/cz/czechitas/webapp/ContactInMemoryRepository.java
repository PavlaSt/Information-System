package cz.czechitas.webapp;

import java.util.*;
import java.util.concurrent.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

@Repository

public class ContactInMemoryRepository implements InterfaceRepository {

    Long sequence = 100L;
    List<Contact> contacts = new ArrayList<>();

    public ContactInMemoryRepository() {
        save(new Contact("Thomas Alva Edison", "+1-123-555-666", "thomas@edison.com"));
        save(new Contact("Albert Einstein", "+41 953 203 569", "albert.einstein@cern.ch"));
        save(new Contact("Kamil Ševeček", "+420 604 111 222", "kamil.sevecek@czechitas.cz"));
    }



    @Override
    public synchronized List<Contact> findAll() {
        List<Contact> copyOfContacts = new ArrayList<>(contacts.size());
        for (Contact contact : contacts) {
            copyOfContacts.add(clone(contact));
        }
        return copyOfContacts;
    }

    @Override
    public Contact findById(Long id) {
        int index = findRecordIndex(id);
        if (index == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return clone(contacts.get(index));
    }

    @Override
    public void save(Contact recordToSave) {
        if (recordToSave.getId() != null) {
            int index = findRecordIndex(recordToSave.getId());
            if (index != -1) {
                contacts.set(index, clone(recordToSave));
                return;
            }
        }
        recordToSave.setId(sequence++);
        contacts.add(clone(recordToSave));
    }



    @Override
    public void deleteById(Long id) {
        int index = findRecordIndex(id);
        if (index != -1) {
            contacts.remove(index);
        }
    }
    /******************************************************/

    private int findRecordIndex(Long id) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (contact.getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private Contact clone(Contact originalContact) {
        return new Contact(
                originalContact.getId(),
                originalContact.getName(),
                originalContact.getPhone(),
                originalContact.getEmail());
    }
}
