package nl.hu.greenify.core.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import nl.hu.greenify.core.application.exceptions.NotFoundException;

@RestControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleSurveyNotFoundException(NotFoundException exception) {
        return createErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return createErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception exception) {
        return createErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Helper to create proper error responses.
     * 
     * @param errorMessage The error message to return
     * @param status The status of the response
     * @return A response entity with the given error message and status
     */
    private ResponseEntity<String> createErrorResponse(String errorMessage, HttpStatus status) {
        // Note: We create a json object here. This is best for the front-end to parse the error message. 
        // Also, this way we can easily add more information in the future. Form validation for example.
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode errorResponse = objectMapper.createObjectNode();
        errorResponse.put("message", errorMessage);

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(errorResponse.toString());
    }
}
