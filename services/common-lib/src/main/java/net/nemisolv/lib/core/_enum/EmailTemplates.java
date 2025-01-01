package net.nemisolv.lib.core._enum;

import lombok.Getter;

public enum EmailTemplates {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation"),
    CONFIRM_REGISTRATION_ACCOUNT("customer-registration.html", "Welcome to TechShop"),
    PASSWORD_RESET("password-reset.html", "Password reset")


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