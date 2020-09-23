package cz.czechitas.webapp;

import java.util.*;
import java.util.concurrent.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
public class HlavniController {
//    Long sequence = 100L;
////    List<Contact> contacts = new CopyOnWriteArrayList<>(
////            Arrays.asList(
////                    new Contact(sequence++, "Thomas Alva Edison", "+1-123-555-666", "thomas@edison.com"),
////                    new Contact(sequence++, "Albert Einstein", "+41 953 203 569", "albert.einstein@cern.ch"),
////                    new Contact(sequence++, "Kamil Ševeček", "+420 604 111 222", "kamil.sevecek@czechitas.cz")
////            )
////    );

    private ContactRepository contactsR;

    public HlavniController() {
        this.contactsR = new ContactRepository();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView displayIndex() {
        return new ModelAndView("redirect:/contacts");
    }

    @RequestMapping("/contacts")
    public ModelAndView displayContacts() {
        ModelAndView drzakNaDataAJmenoSablony = new ModelAndView("index");
        drzakNaDataAJmenoSablony.addObject("contacts", contactsR.contacts);
        return drzakNaDataAJmenoSablony;
    }

    @RequestMapping(value = "/contacts/{number}", method = RequestMethod.POST, params = "_method=DELETE")
    public ModelAndView deleteContact(@PathVariable ("number") Long number) {
       contactsR.deleteContactByNumber(number);
       return new ModelAndView("redirect:/contacts");
    }

//    private void deleteContactByNumber(Long number) {
//        contactsR.contacts.removeIf(contact -> contact.getNumber().equals(number));
////        for (Contact contact : contacts) {
////            if (contact.getNumber().equals(number)) {
////                contacts.remove(contact);
////            }
////
////        }
////        contacts.remove(getContactByNumber(number));
//    }

    @RequestMapping(value = "/edit/{number}", method = RequestMethod.GET)
    public ModelAndView displayEdit(@PathVariable ("number") Long number){
        ModelAndView modelAndViewHolder = new ModelAndView("edit");
        modelAndViewHolder.addObject("contact", contactsR.getContactByNumber(number));
        return modelAndViewHolder;
    }
    @RequestMapping(value = "/edit/{number}", method = RequestMethod.POST)
    public ModelAndView processEdit(@PathVariable ("number") Long number, EditForm editForm ) {
        contactsR.editContact(number, editForm);
        return new ModelAndView("redirect:/contacts");

    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView displayNew() {
        ModelAndView modelAndViewHolder = new ModelAndView("edit");
        modelAndViewHolder.addObject("contact", new EditForm());
        return modelAndViewHolder;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView processNew(EditForm editForm) {
        contactsR.saveContact(editForm);
        return new ModelAndView("redirect:/contacts");
    }

//    private void saveContact(EditForm editForm) {
//        contacts.add(new Contact(sequence++, editForm.getName(), editForm.getPhone(), editForm.getEmail()));
//    }
//
//    private void editContact(Long number, EditForm editForm) {
//        for (Contact contact : contacts) {
//            if (contact.getNumber().equals(number)) {
//                contact.setName(editForm.getName());
//                contact.setPhone(editForm.getPhone());
//                contact.setEmail(editForm.getEmail());
//            }
//
//        }
//    }
//
//    private Contact getContactByNumber(Long number) {
//        for (Contact contact : contacts) {
//            if (contact.getNumber().equals(number)) {
//                return contact;
//            }
//        }
//        return null;
//    }
////    private Contact ziskejContactPodleCisla(Long cislo) {
////        int index  = ziskejIndexContactuPodleCisla(cislo);
////        return contacts.get(index);
////    }
////
////    private int ziskejIndexContactuPodleCisla(Long cislo) {
////        for (int i = 0; i < contacts.size(); i++) {
////            if (contacts.get(i).getNumber().equals(cislo)) {
////                return i;
////            }
////        }
////        return -1;
////    }
}
