package com.contact.controller;

import com.contact.db.Contact;
import com.contact.dto.LoginData;
import com.contact.dto.RegisterData;
import com.contact.operation.UserOperation;
import com.contact.service.SessionOperation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotSecureController {

    @Autowired
    UserOperation userOperation;
    @Autowired
    HttpSession httpSession;
    @Autowired
    SessionOperation sessionOperation;

    @GetMapping("/home")
    public String home(Model model) {



        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);
        if (name == null) {
             model.addAttribute("log", "logout");
        }
        else {
            model.addAttribute("log", "login");
        }

        return "home";
    }
    @GetMapping("/newHome")
    public String getMethodName() {

        sessionOperation.addUserNameInSession();
        return "redirect:/home";
    }
    

    // @PostMapping("/login_process")
    // public void postMethodName(Model model, @ModelAttribute("user") LoginData loginData) {
    //     String username = loginData.getEmail();
    //     String password = loginData.getPassword();
    //     System.out.println("\n\n\n##########\n Email" + username + "\n Password" + password + "\n\n\n");
    // }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String customError, Model model, @ModelAttribute("user") LoginData loginData) {
        if (customError == "invalid_credentials") {
            model.addAttribute("error", "Invalid username or password.");
        } else if (customError != null) {

            model.addAttribute("error", "Invalid username or password.");
        }

        model.addAttribute("log", "logout");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") RegisterData registerData, Model model) {

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);

        return "register";
    }

    @PostMapping("/registered")
    public String registered(@Valid @ModelAttribute("user") RegisterData registerData, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        String operation = userOperation.addUserInDB(registerData);

        switch (operation) {
            case "email":
                model.addAttribute("Error", "Email already exists.");
                break;
            case "success":
                return "redirect:/home";
            default:
                model.addAttribute("Error", "Registration failed: " + operation);
                break;
        }

        model.addAttribute("message", "Please try with another email.");
        return "register";
    }


    @GetMapping("/check")
    public String getMethodName(Model model) {
        Contact users = new Contact();
        model.addAttribute("users", users);
        model.addAttribute("userContact",new Contact());
        return "profileReplace";
    }
    @GetMapping("/nothing")
    public String nothing() {
        return null;
    }
}