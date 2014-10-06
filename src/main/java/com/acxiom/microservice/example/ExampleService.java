package com.acxiom.microservice.example;

import com.acxiom.microservice.json.ValueObjectList;
import com.google.common.base.Optional;

public class ExampleService {

	public ValueObjectList<Example> getList() {
		return ValueObjectList.<Example>newBuilder()
				.addData(new Example("example1"))
				.addData(new Example("example2"))
				.addData(new Example("example3"))
				.setTotal(3)
				.build();
	}

	public Optional<Example> getOne(int exampleId) {
		if(exampleId>=1 && exampleId<=3){
			return Optional.of(new Example("example3"));
		}else{
			return Optional.absent();
		}
	}

}
