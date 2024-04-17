package nl.hu.greenify.security.application.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
