package nl.hu.greenify.core.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class Controller {

    /**
     * Helper to create proper response entities.
     * 
     * @param body The response body to return
     * @param status The status of the response
     * @return A response entity with the given body and status
     */
    protected ResponseEntity<?> createResponse(Object body, HttpStatus status) {
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(body);
    }

    /**
     * Helper to create proper response entities with status OK.
     * 
     * @param body The response body to return
     * @return A response entity with the given body and status OK
     */
    protected ResponseEntity<?> createResponse(Object body) {
        return this.createResponse(body, HttpStatus.OK);
    }
}
