package com.contact.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.contact.operation.UserOperation;

import jakarta.validation.constraints.Size;

public class LoginData {
    @Autowired
    UserOperation userOperation;

    @Size(min = 2, max = 20, message = "{LoginData.email}")
    public String email;
    @Size(min = 4, max = 16, message = "{LoginData.password}")
    public String password;


    // public boolean check;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
