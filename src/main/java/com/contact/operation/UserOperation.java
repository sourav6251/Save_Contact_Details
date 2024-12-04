package com.contact.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.contact.db.Users;
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


}
