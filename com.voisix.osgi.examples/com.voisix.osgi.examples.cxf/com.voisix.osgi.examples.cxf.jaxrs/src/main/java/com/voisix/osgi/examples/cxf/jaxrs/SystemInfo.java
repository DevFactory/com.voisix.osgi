package com.voisix.osgi.examples.cxf.jaxrs;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@WebService
public interface SystemInfo {
	
	@GET
	@Path("/get/")
	@Produces(MediaType.APPLICATION_JSON)
	InfoBean getSystemInfo();
}
