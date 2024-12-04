package com.contact.controller;

import com.contact.db.Contact;
import com.contact.db.Users;
import com.contact.dto.ContactData;
import com.contact.operation.DBOperations;
import com.contact.operation.UserOperation;
import com.contact.service.SessionOperation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/user")
public class SecureController {

    @Autowired
    private S3Service s3Service;
    @Autowired
    UserOperation userOperation;
    @Autowired
    HttpSession httpSession;
    @Autowired
    DBOperations dbOperations;
    @Autowired
    SessionOperation sessionOperation;

    @RequestMapping("/logout")
    public String logout(Model model) {

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);
        return "logout";
    }

    @RequestMapping("/profile")
    public String profile(Model model) {
        sessionOperation.addUserNameInSession();

        String name = userOperation.getUsernameUsingSession();
        model.addAttribute("name", name);
        Users users = userOperation.getUserDB();
        model.addAttribute("users", users);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(@ModelAttribute("users") Users users, Model model) {

        String name = userOperation.getUsernameUsingSession();
        model.addAttribute("name", name);
        Users usersInfo = userOperation.getUserDB();
        model.addAttribute("users", usersInfo);
        return "editProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("users") Users users, BindingResult result, Model model,
                                @RequestParam("file") MultipartFile file) {
        System.out.println("Enter into UpdateProfile");
        if (result.hasErrors()) {
            return "editProfile";
        }
        dbOperations.updateUser(users);
        return "redirect:/user/profile";
    }

    @GetMapping("/contact")
    public String getContact(@ModelAttribute("user") ContactData contactData, Model model) {

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);
        List<Contact> contactData2 = dbOperations.showContact(sessionOperation.getUserId());
        model.addAttribute("contacts", contactData2);

        Users users = dbOperations.getUsers(sessionOperation.getUserEmail());
        model.addAttribute("users", users);

        Contact userContact = new Contact();
        model.addAttribute("users", userContact);
        return "showContact";
    }

    @RequestMapping("/addContact")
    public String addContact(@ModelAttribute("user") ContactData contactData, Model model) {

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);
        return "addContact";
    }

    @RequestMapping("/submit-contact")
    public String submitContact(@Valid @ModelAttribute("user") ContactData contactData, BindingResult result) {

        if (result.hasErrors()) {
            return "addContact";

        }
        dbOperations.addContact(contactData, sessionOperation.getUserId());
        return "redirect:/user/contact";
    }

    @GetMapping("/contactDetails/{id}")
    public String contactDetails(@PathVariable Long id, Model model) {
        Contact contact = dbOperations.getContact(id);
        // Contact contact = new Contact();
        model.addAttribute("userContact", contact);

        System.out.println("\n\n############" + contact.getName());
        return "profileReplace::contactDetails";
        // return "profileReplace";
    }

    @GetMapping("/editContact={id}")
    public String editContact(@PathVariable Long id, Model model) {

        Contact contact = dbOperations.getContact(id);
        // Contact contact = new Contact();
        System.out.println("\n\n############\n%%" + contact.getName());
        model.addAttribute("userContact", contact);
        return "editContact";
    }

    @PostMapping("/updateContact")
    public String updateContact(@ModelAttribute("userContact") Contact contact, Model model) {
        System.out.println("\n\n##########\nname:" + contact.getName() + "\nemail:" + contact.getEmail());

        dbOperations.updateContact(contact);
        return "redirect:contact";
    }

    @GetMapping("/deleteContact={id}")
    public String deleteContact(@PathVariable Long id, Model model) {
        dbOperations.deleteContact(id);

        return "redirect:contact";
    }
}