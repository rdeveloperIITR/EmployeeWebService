package com.rohitdeveloper.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;


import com.rohitdeveloper.service.entity.Employee;
import com.rohitdeveloper.service.mysql.Mysql;



@Path("/employees")
public class RestService {
	
       
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeJSON(@Context UriInfo uriInfo) {
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().clone();
	    // Given a list of employees
		List<Employee> employees=Mysql.selectAllQuery(); 
	    // Convert to GenericEntity and return in response    
	    GenericEntity<List<Employee>> entities = new GenericEntity<List<Employee>>(employees){};
	    return Response
	        .ok(entities)
	        .location(uriBuilder.build())
	        .build();
	}
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeJSONById(@PathParam("param") Integer id,@Context UriInfo uriInfo) {
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().clone();
	    // Given a employee
		Employee employee=Mysql.selectByIdQuery(id);  
	    return Response
	        .ok(employee)
	        .location(uriBuilder.build())
	        .build();
	}
	
	
	@POST                                                //create a rest client
	@Consumes(MediaType.APPLICATION_JSON)            //to tell that raw data is in json format
	public Response insertEmployeeInJSON(Employee employee) {
        Integer empId=Mysql.insertQuery(employee.getEmployeename(),employee.getDesignation(),employee.getSalary());
		return Response.status(201).entity(empId).build();
	}
	
	@PUT                                              //create a rest client
	@Path("/{param}")
	@Consumes(MediaType.APPLICATION_JSON)            //to tell that raw data is in json format
	public Response updateEmployeeInJSON(@PathParam("param") Integer id, Employee employee) {
		boolean isUpdated=Mysql.updateQuery(id,employee.getEmployeename(),employee.getDesignation(),employee.getSalary());
		String result="Update not successful";
		if(isUpdated) result="Update successful";
		return Response.status(201).entity(result).build();
	}
	
	@DELETE
	@Path("/{param}")
	public Response updateEmployeeInJSON(@PathParam("param") Integer id) {
		boolean isDeleted=Mysql.deleteQuery(id);
		String result="Delete not successful";
		if(isDeleted) result="Delete successful";
		return Response.status(201).entity(result).build();
	}
}
