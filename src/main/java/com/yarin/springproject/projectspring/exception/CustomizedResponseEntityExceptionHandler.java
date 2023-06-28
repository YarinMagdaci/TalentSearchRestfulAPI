package com.yarin.springproject.projectspring.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * CustomizedResponseEntityExceptionHandler is a controller advice class that handles exceptions thrown by Rest Controllers.
 * It extends the ResponseEntityExceptionHandler class, which is the base class for handling exceptions in Spring MVC.
 * The ControllerAdvice annotation instructs all controllers to use this class for exception handling.
 * The class defines various exception handler methods that handle specific exceptions and return appropriate error responses.
 */
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception handler method for handling general exceptions.
     * It creates an ErrorDetails object with the current timestamp, exception message, and request description,
     * and returns a ResponseEntity with the error details and an HTTP status of INTERNAL_SERVER_ERROR.
     *
     * @param ex      the exception that was thrown
     * @param request the web request that resulted in the exception
     * @return a ResponseEntity containing the error details and the HTTP status code
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler method for handling JobNotFoundException.
     * It creates an ErrorDetails object with the current timestamp, formatted error message, and request description,
     * and returns a ResponseEntity with the error details and an HTTP status of NOT_FOUND.
     *
     * @param ex      the JobNotFoundException that was thrown
     * @param request the web request that resulted in the exception
     * @return a ResponseEntity containing the error details and the HTTP status code
     */
    @ExceptionHandler(JobNotFoundException.class)
    public final ResponseEntity<Object> handleJobNotFoundException(JobNotFoundException ex, WebRequest request) {
        String errorMessage = "Error: " + ex.getMessage() + " was not found!";
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errorMessage, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler method for handling RecruiterNotFoundException.
     * It creates an ErrorDetails object with the current timestamp, formatted error message, and request description,
     * and returns a ResponseEntity with the error details and an HTTP status of NOT_FOUND.
     *
     * @param ex      the RecruiterNotFoundException that was thrown
     * @param request the web request that resulted in the exception
     * @return a ResponseEntity containing the error details and the HTTP status code
     */
    @ExceptionHandler(RecruiterNotFoundException.class)
    public final ResponseEntity<Object> handleRecruiterNotFoundException(RecruiterNotFoundException ex, WebRequest request) {
        String errorMessage = "Error: " + ex.getMessage() + " was not found!";
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errorMessage, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Overrides the handleMethodArgumentNotValid method of the base class to handle MethodArgumentNotValidException.
     * It creates an ErrorDetails object with the current timestamp, default error message from the field error,
     * and request description, and returns a ResponseEntity with the error details and an HTTP status of BAD_REQUEST.
     *
     * @param ex      the MethodArgumentNotValidException that was thrown
     * @param headers the headers for the response
     * @param status  the HTTP status code for the response
     * @param request the web request that resulted in the exception
     * @return a ResponseEntity containing the error details and the HTTP status code
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                ex.getFieldError().getDefaultMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
