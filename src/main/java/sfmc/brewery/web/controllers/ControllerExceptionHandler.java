package sfmc.brewery.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException ce) {
        return new ResponseEntity<>(ce.getConstraintViolations().stream()
            .map(constraintViolation ->
                    constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage())
                .reduce("", (a, b) -> a + b + "\n"),
        HttpStatus.BAD_REQUEST);
    }
}
