package com.contact.controller;

import com.contact.db.Users;
import com.contact.dto.ContactData;
import com.contact.operation.DBOperations;
import com.contact.service.SessionOperation;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
public class SecureController {

    @Autowired
    private HttpSession httpSession;
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

    @RequestMapping("/contact")
    public String contact(@ModelAttribute("user") ContactData contactData, Model model) {

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);
        return "contact";
    }

    @RequestMapping("/submit-contact")
    public String submitContact(@Valid @ModelAttribute("user") ContactData contactData, BindingResult result) {

        if (result.hasErrors()) {
            return "contact";

        }
        dbOperations.addContact(contactData, 1);
        return "redirect:home";
    }

    @RequestMapping("/profile")
    public String requestMethodName(Model model) {
        sessionOperation.addUserNameInSession();

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);
        Users users = dbOperations.getUsers(sessionOperation.getUserEmail());
        model.addAttribute("photo", users.getImage_url());
        model.addAttribute("email", users.getEmail());
        model.addAttribute("password", users.getPassword());
        String about = users.getAbout();
        if (about == null) {
            about = "Add Something about yourself";
        }

        model.addAttribute("about", about);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String getMethodName(@ModelAttribute("users") Users users, Model model) {

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);
        Users usersInfo = dbOperations.getUsers(sessionOperation.getUserEmail());

        String about = usersInfo.getAbout();
        if (about == null) {
            about = "Add Something about yourself";
        }
        usersInfo.setAbout(about);
        model.addAttribute("users", usersInfo);
        return "editProfile";
    }

    @PostMapping("/updateProfile")
    public String postMethodName(@Valid @ModelAttribute("users") Users users, BindingResult result, Model model) {
        System.out.println("Enter into UpdateProfile");

        if (result.hasErrors()) {
            return "editProfile";
        }
        dbOperations.updateUser(users);
        return "redirect:/user/profile";
    }

}