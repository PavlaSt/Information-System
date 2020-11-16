package cz.czechitas.webapp;

import java.util.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller

public class MainController {

    private InterfaceRepository contactRepository;

    public MainController(InterfaceRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView displayIndex() {
        return new ModelAndView("redirect:/contacts");
    }

    @RequestMapping("/contacts")
    public ModelAndView displayContacts() {
        ModelAndView drzakNaDataAJmenoSablony = new ModelAndView("index");
        List<Contact> contactList = contactRepository.findAll();
        drzakNaDataAJmenoSablony.addObject("contacts", contactRepository.findAll());
        return drzakNaDataAJmenoSablony;
    }


    @RequestMapping(value = "/edit/{number:[0-9]+}", method = RequestMethod.GET)
    public ModelAndView displayEdit(@PathVariable ("number") Long number){
        Contact foundContact = contactRepository.findById(number);
        if (foundContact == null) {
            return new ModelAndView("redirect:/contacts");
        }
        EditForm editForm = new EditForm();
        editForm.setName(foundContact.getName());
        editForm.setPhone(foundContact.getPhone());
        editForm.setEmail(foundContact.getEmail());

        ModelAndView modelAndViewHolder = new ModelAndView("edit");
        modelAndViewHolder.addObject("contact", editForm);
        return modelAndViewHolder;
    }
    @RequestMapping(value = "/edit/{number:[0-9]+}", method = RequestMethod.POST)
    public ModelAndView processEdit(@PathVariable ("number") Long number, EditForm editForm ) {
        Contact contact = new Contact(
                number,
                editForm.getName(),
                editForm.getPhone(),
                editForm.getEmail());
        contactRepository.save(contact);
        return new ModelAndView("redirect:/contacts");

    }

    @RequestMapping(value = "/contacts/{number:[0-9]+}", method = RequestMethod.POST, params = "_method=DELETE")
    public ModelAndView deleteContact(@PathVariable ("number") Long number) {
        contactRepository.deleteById(number);
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
        Contact contact = new Contact(
                editForm.getName(),
                editForm.getPhone(),
                editForm.getEmail());
        contactRepository.save(contact);
        return new ModelAndView("redirect:/contacts");
    }

}
