package nl.hu.greenify.core.domain.exceptions;

public class SurveyNotFoundException extends RuntimeException {
    public SurveyNotFoundException(String message) {
        super(message);
    }
}
