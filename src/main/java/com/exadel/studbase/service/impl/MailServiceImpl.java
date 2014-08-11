package com.exadel.studbase.service.impl;

import com.exadel.studbase.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public JavaMailSenderImpl getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("<" + javaMailSender.getUsername() + ">");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean sendMail(String from, String password, String to, String subject, String body) {
        javaMailSender.setUsername(from);
        javaMailSender.setPassword(password);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("<" + from + ">");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            return false;
        }
        return true;
    }
}