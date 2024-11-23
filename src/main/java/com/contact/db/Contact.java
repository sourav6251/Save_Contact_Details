package com.contact.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contact_id;
    private String name;
    @Column(unique = true)
    private String number;
    private String email;
    private String about;
    private String image_url;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getImage_url() {
        return image_url;
    }


    public void setImage_url(String image_url) {
        this.image_url = image_url;
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


    public Users getUser() {
        return user;
    }


    public void setUser(Users user) {
        this.user = user;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Contact(String name, String number, String email, String about, String image_url, Users user) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.about = about;
        this.image_url = image_url;
        this.user = user;
    }


    public Contact() {
    }

}
