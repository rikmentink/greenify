package nl.hu.greenify.security.application.exceptions;

public class PasswordIsInvalidException extends RuntimeException {
    public PasswordIsInvalidException(String message) {
        super(message);
    }
}
