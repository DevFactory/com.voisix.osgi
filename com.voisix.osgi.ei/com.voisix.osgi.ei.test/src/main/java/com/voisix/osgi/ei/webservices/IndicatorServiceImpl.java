package com.voisix.osgi.ei.webservices;

import java.math.BigDecimal;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@WebService
public class IndicatorServiceImpl {	
	
	@POST
	@Path("/text/{id}")
	public void textValue(@PathParam("id") String id, String payload) {	
	}
	
	@POST
	@Path("/integer/{id}")
	public void integerValue(@PathParam("id") String id, Integer payload) {	
	}
	
	@POST
	@Path("/decimal/{id}")
	public void decimalValue(@PathParam("id") String id, BigDecimal payload) {		
	}

	@POST
	@Path("/boolean/{id}")
	public void booleanValue(@PathParam("id") String id, Boolean payload) {	
	}
	
	@POST
	@Path("/json/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void jsonValue(@PathParam("id") String id, String payload) {	
	}
}
