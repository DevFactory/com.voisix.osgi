package com.voisix.osgi.examples.cxf.jaxrs;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration
public class SystemInfoTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	SystemInfo systemInfo;
	@Test
	public void testGetSystemInfo() {
		final InfoBean info = systemInfo.getSystemInfo();
		logger.info(info.getMap());
	}
	
}
