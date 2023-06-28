package com.yarin.springproject.projectspring.exception;

import java.time.LocalDateTime;

/**
 * ErrorDetails is a class that represents the details of an error or exception.
 * It contains a timestamp, message, and details of the error.
 */
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    /**
     * Constructs an ErrorDetails object with the specified timestamp, message, and details.
     *
     * @param timestamp the timestamp indicating when the error occurred
     * @param message   the message describing the error
     * @param details   the additional details or context of the error
     */
    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    /**
     * Returns the timestamp indicating when the error occurred.
     *
     * @return the timestamp of the error
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the message describing the error.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the additional details or context of the error.
     *
     * @return the error details
     */
    public String getDetails() {
        return details;
    }
}
