package nl.hu.greenify.security.domain.exceptions;

public class AccountWithoutPersonException extends RuntimeException{
    public AccountWithoutPersonException(String message) {
        super(message);
    }
}
