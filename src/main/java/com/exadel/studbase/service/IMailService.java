package com.exadel.studbase.service;

import org.springframework.mail.MailSender;

public interface IMailService {
    MailSender getMailSender();

    void setMailSender(MailSender mailSender);

    boolean sendMail(String to, String subject, String body);
}