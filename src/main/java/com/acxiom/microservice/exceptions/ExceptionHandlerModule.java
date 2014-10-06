package com.acxiom.microservice.exceptions;

import com.google.inject.AbstractModule;

public class ExceptionHandlerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IllegalArgumentExceptionMapper.class);
        bind(IllegalStateExceptionMapper.class);
        bind(APIExceptionMapper.class);
    }

}
