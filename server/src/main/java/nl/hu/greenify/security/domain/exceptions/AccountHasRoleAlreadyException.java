package nl.hu.greenify.security.domain.exceptions;

public class AccountHasRoleAlreadyException extends RuntimeException{
    public AccountHasRoleAlreadyException(String message) {
        super(message);
    }
}
