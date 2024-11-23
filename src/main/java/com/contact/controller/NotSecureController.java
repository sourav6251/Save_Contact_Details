package com.contact.controller;

import com.contact.dto.LoginData;
import com.contact.dto.RegisterData;
import com.contact.operation.DBOperations;
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
    private DBOperations dbOperations;

    @Autowired
    HttpSession httpSession;

    @Autowired
    SessionOperation sessionOperation;

    @GetMapping("/home")
    public String home(Model model) {

        sessionOperation.addUserNameInSession();

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);


        return "home";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String customError, Model model,@ModelAttribute("user") LoginData loginData) {
        if (customError == "invalid_credentials") {
            model.addAttribute("error", "Invalid username or password.");
        }
        else if (  customError != null) {
            
            model.addAttribute("error", "Invalid username or password.");
        }
        return "login";
    }

    // @PostMapping("/login_process")
    // public String login(@Valid @ModelAttribute("user") LoginData loginData, BindingResult result, Model model) {
    //     if (result.hasErrors()) {
    //         return "login";
    //     }
    //     boolean isAuthenticated = dbOperations.authenticate(loginData.getEmail(), loginData.getPassword());
    //     if (!isAuthenticated) {
    //         model.addAttribute("loginError", "Invalid email or password.");
    //         return "login";
    //     }
    //     return "redirect:/profile";
    // }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") RegisterData registerData,Model model) {

        String name = (String) httpSession.getAttribute("name");
        model.addAttribute("name", name);

        return "register";
    }

    @PostMapping("/registered")
    public String registered(@Valid @ModelAttribute("user") RegisterData registerData, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        String operation = dbOperations.register(registerData);

        switch (operation) {
            case "email":
                model.addAttribute("Error", "Email already exists. Please try another.");
                break;
            case "success":
                return "redirect:/home";
            default:
                model.addAttribute("Error", "Registration failed: " + operation);
                break;
        }

        model.addAttribute("message", "Please register again.");
        return "register";
    }

    @GetMapping("/profile")
    public String profile(@Valid @ModelAttribute("user") LoginData loginData, BindingResult result,Model model) {
        
        // boolean isLogin=sessionOperation.isLogin();
        model.addAttribute("isLogin", false);

        if (result.hasErrors()) {
            return "login";
        }
        return "profile"; 
    }
}