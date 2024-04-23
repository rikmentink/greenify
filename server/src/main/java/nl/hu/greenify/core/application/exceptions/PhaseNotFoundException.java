package nl.hu.greenify.core.application.exceptions;

public class PhaseNotFoundException extends IllegalArgumentException {
    public PhaseNotFoundException(String message) {
        super(message);
    }
}
