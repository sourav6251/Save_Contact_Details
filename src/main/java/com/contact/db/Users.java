package com.contact.db;

import java.util.List;
import jakarta.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;
    private String name;
    @Column(length = 400)
    private String password;
    @Column(unique = true)
    private String email;
    @Column(length = 500)
    private String about;
    private String image_url;
    private boolean enable;
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contact = new ArrayList<>();

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    
    public String getImage_url() {
        return image_url;
    }


    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isEnable() {
        return enable;
    }


    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public String getAbout() {
        return about;
    }


    public void setAbout(String about) {
        this.about = about;
    }


    public List<Contact> getContact() {
        return contact;
    }


    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }


    public Users(String name, String password, String email, String about, String image_url,
                 boolean enable, String role, List<Contact> contact) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.about = about;
        this.image_url = image_url;
        this.enable = enable;
        this.role = role;
        this.contact = contact;
    }


    public Users() {
    }
}
