package sfmc.brewery.web.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final String INTERNAL_ERROR = "Something went wrong while accessing the database";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException ce) {
        return new ResponseEntity<>(ce.getConstraintViolations().stream()
            .map(constraintViolation ->
                    constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage())
                .reduce("", (a, b) -> a + b + "\n"),
        HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleNotFound(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleInternalError() {
        return new ResponseEntity<>(INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
