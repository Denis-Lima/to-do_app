package br.com.todoserver.todoapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.todoserver.todoapp.exceptions.ErrorResponse;
import br.com.todoserver.todoapp.exceptions.ResourceAlreadyExistsException;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        List<String> details = new ArrayList<String>();
        details.add(exception.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<Object> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException exception, WebRequest request) {
        List<String> details = new ArrayList<String>();
        details.add(exception.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Resource exists", details);
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<String>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
