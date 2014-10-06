package com.acxiom.microservice.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Provider
@Singleton
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {

    private static final Logger LOG = LoggerFactory.getLogger(IllegalStateExceptionMapper.class);

    @Override
    public Response toResponse(IllegalStateException exception) {
        LOG.warn("Exception mapped to response", exception);

        return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(exception.getMessage())
                .build();
    }
}
