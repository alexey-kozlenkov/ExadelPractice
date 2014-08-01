package com.exadel.studbase.service.mail;

import org.springframework.mail.MailSender;

public interface MailService {
    public MailSender getMailSender();

    public void setMailSender(MailSender mailSender);

    public boolean sendMail(String to, String subject, String body);
}