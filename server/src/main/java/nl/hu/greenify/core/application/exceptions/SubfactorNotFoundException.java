package nl.hu.greenify.core.application.exceptions;

public class SubfactorNotFoundException extends IllegalArgumentException {
    public SubfactorNotFoundException(String message) {
        super(message);
    }
}
