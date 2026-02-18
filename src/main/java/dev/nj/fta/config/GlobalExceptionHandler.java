package dev.nj.fta.config;

import dev.nj.fta.developer.DeveloperAlreadyExistsException;
import dev.nj.fta.developer.DeveloperNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        LOGGER.warn("Validation errors: {}", errors);
        return ResponseEntity.badRequest().body(Map.of("messages", errors));
    }

    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<?> handleDeveloperNotFoundException(DeveloperNotFoundException ex) {
        LOGGER.warn("Developer not found: {}", ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DeveloperAlreadyExistsException.class)
    public ResponseEntity<?> handleDeveloperAlreadyExistsException(DeveloperAlreadyExistsException ex) {
        LOGGER.warn("Developer already exists");
        return ResponseEntity.badRequest().build();
    }
}
