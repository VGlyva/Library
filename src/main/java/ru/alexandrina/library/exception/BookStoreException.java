package ru.alexandrina.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.Optional;

@RestControllerAdvice
public class BookStoreException {
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(Errors e) {
        String field = Optional.ofNullable(e.getFieldError())
                .map(FieldError::getField)
                .orElse("<unknown>");
        return ResponseEntity.badRequest().body("Поле/параметр '" + field + "' не валидно!");
    }

    @ExceptionHandler({AuthorNotFoundException.class, BookNotFoundException.class, PublisherNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
