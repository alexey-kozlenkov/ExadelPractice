package com.exadel.studbase.service.impl;

import com.exadel.studbase.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IMailServiceImpl implements IMailService {
    @Autowired
    private MailSender mailSender;

    @Override
    public MailSender getMailSender() {
        return mailSender;
    }

    @Override
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
        } catch (MailException me) {
            me.printStackTrace();
        }
    }
}