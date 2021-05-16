package com.shrikant.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String email,String token){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("Email Verification");
        mail.setFrom("shrikantnavghare2001@gmail.com");
        mail.setText("To confirm your account, please click here: " +
           "http://localhost:8080/confirm?token="+token
        );
        javaMailSender.send(mail);
    }
}
