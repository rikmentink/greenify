package nl.hu.greenify.core.application.exceptions;

public class TemplateNotFoundException extends IllegalArgumentException {
    public TemplateNotFoundException(String message) {
        super(message);
    }
}
