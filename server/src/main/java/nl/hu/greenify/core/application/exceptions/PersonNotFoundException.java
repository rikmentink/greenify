package nl.hu.greenify.core.application.exceptions;

public class PersonNotFoundException extends NotFoundException {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
