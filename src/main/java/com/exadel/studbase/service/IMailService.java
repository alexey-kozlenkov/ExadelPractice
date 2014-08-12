package com.exadel.studbase.service;

public interface IMailService {
    boolean sendMail(String to, String subject, String body);

    boolean sendMail(String from, String password, String to, String subject, String body);
}