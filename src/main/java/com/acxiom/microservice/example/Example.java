package com.acxiom.microservice.example;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import com.acxiom.microservice.json.ValueObject;

public class Example extends ValueObject {
	@JsonProperty("example")
	private final String example;

	@JsonCreator
	public Example(@JsonProperty("example") String example) {
		this.example = example;
	}

	public String getExample() {
		return example;
	}
}
