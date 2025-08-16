// src/main/java/com/aluraforo/api/infra/errors/ApiExceptionHandler.java
package com.aluraforo.api.infra.errors;

import com.aluraforo.api.infra.exceptions.DuplicateTopicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getField(),
                        e -> e.getDefaultMessage(),
                        (a,b) -> a
                ));
        return ResponseEntity.badRequest().body(Map.of(
                "message", "Datos inválidos",
                "errors", errors
        ));
    }

    @ExceptionHandler(DuplicateTopicException.class)
    public ResponseEntity<?> handleDuplicate(DuplicateTopicException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }
}