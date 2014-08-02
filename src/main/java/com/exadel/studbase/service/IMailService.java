package com.exadel.studbase.service;

import org.springframework.mail.MailSender;

public interface IMailService {
    public MailSender getMailSender();

    public void setMailSender(MailSender mailSender);

    public boolean sendMail(String to, String subject, String body);
}