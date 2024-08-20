package com.application.enums;

public enum EmailStatus {

    CONFIRMATION_SIGN_UP("Confirmation Sign-Up", "Please confirm your sign-up by this code : "),
    PASSWORD_RESET("Password Reset", "Use the code below to reset your password."),
    ACCOUNT_ACTIVATION("Account Activation", "Activate your account using the link provided in this email."),
    EMAIL_VERIFICATION("Email Verification", "Verify your email address with the code provided."),

    ConfirmationDone("Confirmation Done","") ,

    ASK_MEETING("ASK to Meeting","")  ;
    private final String subject;
    private final String description;

    EmailStatus(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

}

