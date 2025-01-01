package net.nemisolv.identity.service;


public interface SendMailService {
    void sendEmailConfirmation(String email, String recipientName);
}
