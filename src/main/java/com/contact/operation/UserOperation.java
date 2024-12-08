package com.contact.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.contact.db.Contact;
import com.contact.db.Users;
import com.contact.dto.ContactData;
import com.contact.dto.Password;
import com.contact.dto.RegisterData;
import com.contact.service.SendMail;
import com.contact.service.SessionOperation;

import jakarta.servlet.http.HttpSession;

@Repository
public class UserOperation {

    @Autowired
    HttpSession httpSession;
    @Autowired
    DBOperations dbOperations;
    @Autowired
    SessionOperation sessionOperation;
    @Autowired
    SendMail sendMail;


    public String getUsernameUsingSession() {
        String username = (String) httpSession.getAttribute("name");
        return username;
    }

    public Users getUserDB(){
        Users users =dbOperations.getUsers(sessionOperation.getUserEmail());
        String about = users.getAbout();
        if (about == null) {
            about = "Add Something about yourself";
            users.setAbout(about);
        }
        if (users.getImage_url() ==null) {
            users.setImage_url("http://127.0.0.1:8080/icon/avatar.svg");
        }

        return users;
    }
  
    public String addUserInDB(RegisterData registerData){

        String operation =dbOperations.register(registerData);

        switch (operation) {
            case "email": //Error
                String errorMessage = "Hi Someone try to register with your email address. \n We suggest change your password ";
                String errorSubject = "Alert"; 
                sendMail.registeredMail(registerData.getEmail(), errorSubject, errorMessage);
              
                break;
            case "success":   //success
                String successMessage="Hi!\t"+ registerData.getName() 
                +"\nYou registered successfully. \nand"+registerData.getPassword()
                +" is your password"+"\n\nThank you for registering with us\nEnjoy your jurny with us";  
                String successSubject="Registration Successful";
                sendMail.registeredMail(registerData.getEmail(), successSubject, successMessage);
                break;
            default:
              
                break;
        }

        return operation;
    }

    public void addUserNameInSession(){
        sessionOperation.addUserNameInSession();
    }

    public void updateUser(Users users){
        dbOperations.updateUser(users);
    }

    public String getSessionName(){
        return (String) httpSession.getAttribute("name");
    }

    public List<Contact> getContact(){
        return dbOperations.showContact((Long)httpSession.getAttribute("ID"));
    }

    public void addContact(ContactData contactData){
        dbOperations.addContact(contactData, (Long) httpSession.getAttribute("ID"));
    }

    public Contact getContactById(Long id){
        return dbOperations.getContact(id);
    }

    public void updateContact(Contact contact){
        dbOperations.updateContact(contact);
    }

    /**
     * Deletes a contact from the database by the given contact ID.
     *
     * @param id the ID of the contact to be deleted
     */
    public void deleteContact(Long id){
        dbOperations.deleteContact(id);
    }

    /**
     * Removes the "name" attribute from the HTTP session.
     * */
    public void removeSession(){
        httpSession.removeAttribute("name");
    }

    /**
     * Changes the password for the current user.
     *
     * @param password an object containing the new password and confirmation password
     * @return "samePassword" if the new password is the same as the current password,
     *         "notSame" if the new password and confirmation password do not match,
     *         "passwordNotMatch" if the new password and confirmation password do not match,
     *         "true" if the password was successfully changed
     */
    public String changePassword(Password password){
        if (dbOperations.getPasswordById((Long)httpSession.getAttribute("ID")).equals(password.getNewPassword())) {
           return "samePassword";
        }
        else if (!password.getNewPassword().equals(password.getConfirmPassword())) {
            return "notSame";
        }

        dbOperations.changePassword(password,(Long) httpSession.getAttribute("ID"));
        return "success";
    }


    public String deleteUser(){
        dbOperations.deleteUser((Long) httpSession.getAttribute("ID"));
        return "success";
    }
}
