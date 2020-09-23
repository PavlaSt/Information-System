package cz.czechitas.webapp;

import java.util.*;
import java.util.concurrent.*;

public class ContactRepository {

    Long sequence = 100L;
    List<Contact> contacts = new CopyOnWriteArrayList<>(
            Arrays.asList(
                    new Contact(sequence++, "Thomas Alva Edison", "+1-123-555-666", "thomas@edison.com"),
                    new Contact(sequence++, "Albert Einstein", "+41 953 203 569", "albert.einstein@cern.ch"),
                    new Contact(sequence++, "Kamil Ševeček", "+420 604 111 222", "kamil.sevecek@czechitas.cz")
            )
    );


    public void saveContact(EditForm editForm) {
        contacts.add(new Contact(sequence++, editForm.getName(), editForm.getPhone(), editForm.getEmail()));
    }

    public void editContact(Long number, EditForm editForm) {
        for (Contact contact : contacts) {
            if (contact.getNumber().equals(number)) {
                contact.setName(editForm.getName());
                contact.setPhone(editForm.getPhone());
                contact.setEmail(editForm.getEmail());
            }

        }
    }

    public Contact getContactByNumber(Long number) {
        for (Contact contact : contacts) {
            if (contact.getNumber().equals(number)) {
                return contact;
            }
        }
        return null;
    }
//    private Contact ziskejContactPodleCisla(Long cislo) {
//        int index  = ziskejIndexContactuPodleCisla(cislo);
//        return contacts.get(index);
//    }
//
//    private int ziskejIndexContactuPodleCisla(Long cislo) {
//        for (int i = 0; i < contacts.size(); i++) {
//            if (contacts.get(i).getNumber().equals(cislo)) {
//                return i;
//            }
//        }
//        return -1;
//    }

    public void deleteContactByNumber(Long number) {
        contacts.removeIf(contact -> contact.getNumber().equals(number));
//        for (Contact contact : contacts) {
//            if (contact.getNumber().equals(number)) {
//                contacts.remove(contact);
//            }
//
//        }
//        contacts.remove(getContactByNumber(number));
    }

}
