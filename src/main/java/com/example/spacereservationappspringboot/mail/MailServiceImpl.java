package com.example.spacereservationappspringboot.mail;

public interface MailServiceImpl {
    void sendSimpleEmail(String to, String subject, String text);
    void sendSimpleEmailWithAttachment(String to, String subject, String htmlBody);
}
