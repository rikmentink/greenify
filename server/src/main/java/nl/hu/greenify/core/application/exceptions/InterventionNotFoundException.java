package nl.hu.greenify.core.application.exceptions;

public class InterventionNotFoundException extends NotFoundException {
    public InterventionNotFoundException(Long id) {
        super("Intervention with id " + id + " does not exist");
    }
}
