package nl.hu.greenify.security.controller.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
