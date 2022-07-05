package com.example.demospringrest.exception;

import com.example.demospringrest.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceException(ResourceNotFoundException e, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> apiException(ApiException e, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), webRequest.getDescription(true));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(Exception e, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> error = new HashMap<>();

        BindingResult bindingResult = ex.getBindingResult();
        Iterator<FieldError> iterator = bindingResult.getFieldErrors().iterator();
        while (iterator.hasNext()) {
            FieldError next = iterator.next();
            String field = next.getField();
            String message = next.getDefaultMessage();
            error.put(field, message);
        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
