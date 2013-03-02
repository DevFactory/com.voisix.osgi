package com.voisix.osgi.examples.cxf.jaxrs;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TypeTestImpl implements TypeTest {
	private final static Log logger = LogFactory.getLog(TypeTest.class);

	@Override
	@POST
	@Path("/log")
	@Consumes("application/json")
	public void log(AbstractType type) {
		logger.info(type.getClass().getName());
	}

	@Override
	@POST
	@Path("/logList")
	@Consumes("application/json")
	public void logList(List<AbstractType> typeList) {
		for (AbstractType type : typeList) {
			logger.info(type.getClass().getName());
		}		
	}

}
