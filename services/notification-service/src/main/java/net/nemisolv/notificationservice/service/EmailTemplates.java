package net.nemisolv.notificationservice.service;

import lombok.Getter;

public enum EmailTemplates {

    WELCOME_EMAIL("welcome-email.html", "Welcome to TechShop"),
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation"),
    ACCOUNT_REGISTRATION("customer-registration.html", "Confirmation Registration!"),

    PASSWORD_RESET("password/password-reset.html", "Password reset")




    ;

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}