package com.cotodel.hrms.auth.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.auth.server.entity.UserEntity;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    
    @Value("${spring.mail.username}")
	private String hrmsEmailId;
    
    

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
    
    public void sendMailMassage(String subject,String message,UserEntity userEntity) {
    	
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEntity.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setFrom(hrmsEmailId);
        mailMessage.setText(message);
        sendEmail(mailMessage);
    	
    }

}