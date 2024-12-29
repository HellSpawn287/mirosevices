package com.hellspawn287.user.controller;

import com.hellspawn287.user.model.dto.FieldErrorDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceController {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleEntityNotFoundException(EntityNotFoundException exception) {
        log.warn("Could not find entity: ", exception);


    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    FieldErrorDto handleConstraintViolationException(ConstraintViolationException exception) {
        log.warn("Validation failed: ", exception);
        return new FieldErrorDto(exception.getMessage(), null);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    List<FieldErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn("Validation failed: ", exception);
        return exception.getFieldErrors().stream()
                .map(fieldError -> new FieldErrorDto(fieldError.getDefaultMessage(), fieldError.getField()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    void handleEntityExistsException(EntityExistsException exception) {
        log.warn("Entity already exist: ", exception);
    }

}
