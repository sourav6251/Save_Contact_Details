package com.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String message) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }

    public void registeredMail(String email, String subject, String message) {
        try {
            sendMail(email, subject, message);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void loginMail(String email, String subject, String message) {
        try {
            sendMail(email, subject, message);

        } catch (Exception e) {
            e.getMessage();
        }
    }
}
