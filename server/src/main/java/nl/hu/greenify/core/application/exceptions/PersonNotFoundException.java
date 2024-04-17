package nl.hu.greenify.core.application.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Long id) {
        super("Person with id " + id + " does not exist");
    }
}
