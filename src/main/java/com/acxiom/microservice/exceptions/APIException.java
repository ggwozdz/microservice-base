package com.acxiom.microservice.exceptions;

import javax.ws.rs.core.Response.Status;

import com.acxiom.microservice.json.ValueObject;

public class APIException extends Exception {

    private static final long serialVersionUID = 1L;
    private final static String ERR_MSG_FORMAT = "%d:%s";

    private final int statusCode;
    private final ValueObject details;

    public APIException(Status status, ValueObject details) {
        super(String.format(ERR_MSG_FORMAT, status.getStatusCode(), details.toString()));
        this.statusCode = status.getStatusCode();
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ValueObject getDetails() {
        return details;
    }
}
