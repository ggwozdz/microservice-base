package com.acxiom.microservice.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Provider
@Singleton
public class APIExceptionMapper implements ExceptionMapper<APIException> {

    private static final Logger LOG = LoggerFactory.getLogger(APIExceptionMapper.class);

    @Override
    public Response toResponse(APIException exception) {     
        LOG.info("Exception mapped to response. ", exception);

        return Response
                .status(exception.getStatusCode())
                .entity(exception.getDetails())
                .build();
    }
}
