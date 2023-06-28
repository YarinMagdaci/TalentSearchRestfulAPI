package com.yarin.springproject.projectspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * JobNotFoundException is an exception that is thrown when a job is not found or does not exist in the database.
 * It is typically thrown from a RestController to indicate that a requested job could not be found.
 * This exception is annotated with @ResponseStatus to specify the HTTP status code that should be returned
 * when this exception is thrown. In this case, it is set to HttpStatus.NOT_FOUND, indicating a "404 Not Found" status.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class JobNotFoundException extends RuntimeException {

    /**
     * Constructs a JobNotFoundException with the specified error message.
     *
     * @param message the error message describing the job not found situation
     */
    public JobNotFoundException(String message) {
        super(message);
    }
}
