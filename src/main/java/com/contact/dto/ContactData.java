package com.contact.dto;


import jakarta.validation.constraints.Size;

public class ContactData {


    @Size(min = 3,max = 20)
    private String name;
    @Size(min = 10,max = 10,message = "* Size must be {min}")
    private String number;
    private String email;
    @Size(max = 100,message = "Within 100 character")
    private String about;
    public ContactData(String name, String number, String email, String about) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.about = about;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }
}
