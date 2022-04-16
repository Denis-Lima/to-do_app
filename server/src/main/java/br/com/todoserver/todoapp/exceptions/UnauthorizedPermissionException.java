package br.com.todoserver.todoapp.exceptions;

public class UnauthorizedPermissionException extends RuntimeException {
    public UnauthorizedPermissionException(String message) {
        super(message);
    }
}
