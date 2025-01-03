package com.contact.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterData {


    @Size(min = 5, max = 20)
    public String name;
    @Size(min = 5, max = 50)
    public String email;
    @Size(min = 5, max = 20)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,20}$", message = "Password must contain at least one uppercase letter, one digit, and one special character")
    public String password;
    @AssertTrue
    public boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public RegisterData(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


}
