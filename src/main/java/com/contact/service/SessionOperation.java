package com.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.contact.operation.DBOperations;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionOperation {

    @Autowired
    DBOperations dbOperations;
    @Autowired
    private HttpSession session;
    @Autowired
    SendMail sendMail;

    /**
     * Add user name in session
     */
    public void addUserNameInSession() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            if (email !=null) {
                String name = dbOperations.getUsername(email);
                if (session.getAttribute("name") == null) {
                    sendMail.loginMail(email, "Login", "You are login in your account");
                }
                session.setAttribute("name", name);
                session.setAttribute("ID", dbOperations.getUserId(email));
                
            } else {
                
            }

        }

    }
    /**
     * Check user is login or not
     * @return boolean value
     */

    public boolean isLogin() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @return Login Email
     */
    public String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    // public long getUserId() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication != null && authentication.isAuthenticated()) {
    //         return dbOperations.getUserId(authentication.getName());
    //     }
    //     return 0;
    // }

}
