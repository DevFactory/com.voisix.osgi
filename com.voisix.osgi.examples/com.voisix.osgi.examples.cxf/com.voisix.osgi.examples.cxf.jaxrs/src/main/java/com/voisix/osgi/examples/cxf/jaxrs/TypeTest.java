package com.voisix.osgi.examples.cxf.jaxrs;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@WebService
public interface TypeTest {
	
	@POST
	@Path("/log")
	@Consumes(MediaType.APPLICATION_JSON)
	void log(AbstractType type);
	
	@POST
	@Path("/logList")
	@Consumes(MediaType.APPLICATION_JSON)
	void logList(List<AbstractType> typeList);
}
