package com.grandstay.hotel.util.wrappers;

import java.io.Serializable;

/**
 * Standard error response body for API exception handling.
 */
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private String timestamp;
    private String path;
    private String status;
    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
