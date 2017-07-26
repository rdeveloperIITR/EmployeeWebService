package com.rohitdeveloper.service.rest;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.rohitdeveloper.service.entity.Employee;
import com.rohitdeveloper.service.solrsearch.SolrSearch;

@Path("/search")
public class SearchService {
      
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSolrDocumentJSON(@QueryParam("query") String searchQuery,@Context UriInfo uriInfo) {
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder().clone();
		 // Given a list of employees
		List<Employee> employees=SolrSearch.searchSolrDocument(searchQuery);
	    // Convert to GenericEntity and return in response    
	    GenericEntity<List<Employee>> entities = new GenericEntity<List<Employee>>(employees){};
	    return Response
	        .ok(entities)
	        .location(uriBuilder.build())
	        .build();
	}
	
	
	@POST                                            
	@Consumes(MediaType.APPLICATION_JSON)            //to tell that raw data is in json format
	public Response addSolrDocumentInJSON(Employee emp) {
		boolean isDocumentadded = SolrSearch.addSolrDocument(emp.getId(), emp.getEmployeename(),emp.getDesignation(), emp.getSalary());
		String result="Solr Document not added";
		if(isDocumentadded) result="Solr Document added successfully";
		return Response.status(201).entity(result).build();
	}
    
	@DELETE
	@Path("/delete")
	public Response deleteSolrDocumentInJSON(@QueryParam("id") String id) {
		boolean isDocumentdeleted=SolrSearch.deleteSolrDocument(id);
		String result="Solr Document not deleted";
		if(isDocumentdeleted) result="Solr Document deleted successfully";
		return Response.status(201).entity(result).build();
	}
}
