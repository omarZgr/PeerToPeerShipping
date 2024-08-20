package com.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender  javaMailSender ;

    @Value("${spring.mail.username}")
    private String from ;


    public void sendEmail(String to,String subject,String message)
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage() ;
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(from);
        javaMailSender.send(simpleMailMessage);
    }
}
