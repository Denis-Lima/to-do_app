package br.com.todoserver.todoapp.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.todoserver.todoapp.exceptions.ResourceAlreadyExistsException;
import br.com.todoserver.todoapp.responses.ErrorResponse;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Server error: " + exception.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<Object> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse(exception.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            stringBuilder.append(error.getDefaultMessage() + "\n");
        };
        ErrorResponse error = new ErrorResponse(stringBuilder.substring(0, stringBuilder.length() - 1).toString());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
