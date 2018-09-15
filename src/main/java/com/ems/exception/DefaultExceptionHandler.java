package com.ems.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> defaultError(Exception ex) {
        String details = null;
        if (null != ex.getCause()) {
            details = ex.getCause().getMessage();
            if (null != ex.getCause().getCause()) {
                details = ex.getCause().getCause().getMessage();
            }
        }
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(ex.getMessage(), details), new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorMessage {
    private String message;
    private String details;
}
