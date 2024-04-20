package nl.hu.greenify.security.domain.exceptions;

public class AccountDoesntHaveRoleException extends RuntimeException {
    public AccountDoesntHaveRoleException(String message) {
        super(message);
    }
}
