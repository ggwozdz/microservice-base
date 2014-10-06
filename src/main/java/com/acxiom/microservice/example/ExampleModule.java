package com.acxiom.microservice.example;

import com.google.inject.AbstractModule;

public class ExampleModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ExampleResource.class);
		bind(ExampleService.class);
		
	}
	
}
