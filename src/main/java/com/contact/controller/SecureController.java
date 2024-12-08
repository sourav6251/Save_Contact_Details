package com.contact.controller;

import com.contact.db.Contact;
import com.contact.db.Users;
import com.contact.dto.ContactData;
import com.contact.dto.Password;
import com.contact.operation.DBOperations;
import com.contact.operation.UserOperation;
import com.contact.service.SessionOperation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Str;

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
    UserOperation userOperation;
    @Autowired
    HttpSession httpSession;
    @Autowired
    DBOperations dbOperations;
    @Autowired
    SessionOperation sessionOperation;

    @RequestMapping("/logout")
    public String logout(Model model) {
        userOperation.removeSession();
        return "logout";
    }

    @RequestMapping("/profile")
    public String profile(Model model) {

        model.addAttribute("log", "login");
        userOperation.addUserNameInSession();
        Users users = userOperation.getUserDB();
        model.addAttribute("users", users);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(@ModelAttribute("users") Users users, Model model) {

        model.addAttribute("log", "login");
        Users usersInfo = userOperation.getUserDB();
        model.addAttribute("users", usersInfo);
        return "editProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@Valid @ModelAttribute("users") Users users, BindingResult result, Model model,
            @RequestParam("file") MultipartFile file) {

        model.addAttribute("log", "login");
        if (result.hasErrors()) {
            return "editProfile";
        }
        userOperation.updateUser(users);
        return "redirect:/user/profile";
    }

    @GetMapping("/contact")
    public String getContact(@ModelAttribute("user") ContactData contactData, Model model) {

        model.addAttribute("log", "login");
        List<Contact> contactData2 = userOperation.getContact();
        model.addAttribute("contacts", contactData2);
        Users users = userOperation.getUserDB();
        model.addAttribute("users", users);

        return "showContact";
    }

    @RequestMapping("/addContact")
    public String addContact(@ModelAttribute("user") ContactData contactData, Model model) {

        model.addAttribute("log", "login");
        return "addContact";
    }

    @RequestMapping("/submit-contact")
    public String submitContact(@Valid @ModelAttribute("user") ContactData contactData, BindingResult result) {

        
        if (result.hasErrors()) {
            return "addContact";
        }
        userOperation.addContact(contactData);
        return "redirect:/user/contact";
    }

    @GetMapping("/contactDetails/{id}")
    public String contactDetails(@PathVariable Long id, Model model) {
        Contact contact = userOperation.getContactById(id);
        model.addAttribute("userContact", contact);
        return "profileReplace::contactDetails";
    }

    @GetMapping("/editContact={id}")
    public String editContact(@PathVariable Long id, Model model) {
        model.addAttribute("log", "login");
        Contact contact = userOperation.getContactById(id);
        model.addAttribute("userContact", contact);
        return "editContact";
    }

    @PostMapping("/updateContact")
    public String updateContact(@ModelAttribute("userContact") Contact contact, Model model) {

        userOperation.updateContact(contact);
        return "redirect:contact";
    }

    @GetMapping("/deleteContact={id}")
    public String deleteContact(@PathVariable Long id, Model model) {
        userOperation.deleteContact(id);
        return "redirect:contact";
    }

    @GetMapping("/profileDetails")
    public String profileDetails(@ModelAttribute("user") ContactData contactData, Model model) {
        model.addAttribute("log", "login");
        List<Contact> contactData2 = userOperation.getContact();// dbOperations.showContact(sessionOperation.getUserId());
        model.addAttribute("contacts", contactData2);
        Users users = userOperation.getUserDB();
        model.addAttribute("users", users);
        return "profileReplace::profileDetails";
    }
 
    @GetMapping("/profileDetailsEdit")
    public String profileDetailsEdit(@ModelAttribute("user") ContactData contactData, Model model) {
        model.addAttribute("log", "login");
        List<Contact> contactData2 = userOperation.getContact();// dbOperations.showContact(sessionOperation.getUserId());
        model.addAttribute("contacts", contactData2);
        Users users = userOperation.getUserDB();
        model.addAttribute("users", users);
        return "profileReplace::ProfileDetailsEdit";
    }
    
    @GetMapping("/changePassword")
    public String getMethodName(@ModelAttribute("password") Password password,Model model) {
        return "changePassword";
    }
    
    @PostMapping("/changePasswordProcess")
    public String changePasswordProcess(@Valid @ModelAttribute("password") Password password,BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Sourav");
            return "changePassword";
            
        }
        String operation=userOperation.changePassword(password);

        switch (operation) {
            case "samePassword":
                model.addAttribute("error", "Both Password(Old and New) are same");
                return "changePassword";
                
        
            case "notSame":
                model.addAttribute("error", "Both Password(New and Conform ) are not same");
                return "changePassword";
            case "success":
                model.addAttribute("error", "Password Changed Successfully");
                return "changePassword";
        }
        return "redirect:changePassword";
    }
    
    @GetMapping("/Delete-user")
    public String Delete(){
        userOperation.deleteUser();
        return "redirect:logout";
    }
}