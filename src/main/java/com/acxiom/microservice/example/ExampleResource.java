package com.acxiom.microservice.example;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.acxiom.microservice.json.ValueObjectList;
import com.google.common.base.Optional;

@Path("example")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExampleResource {
	private final ExampleService exampleService;
	
	@Inject
	public ExampleResource(ExampleService exampleService) {
		this.exampleService = exampleService;
	}

	@GET
	public ValueObjectList<Example> getExampleList(){
		return exampleService.getList();
	}
	
	@GET
	@Path("{exampleId}")
	public Response getExampleList(@PathParam("exampleId") int exampleId){
		Optional<Example> optional = exampleService.getOne(exampleId);
		if(optional.isPresent()){
			return Response.ok(optional.get()).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
