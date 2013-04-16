package com.voisix.osgi.examples.osgi.ca;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.voisix.osgi.common.AbstractManagedServiceFactory;

public class TestManagedServiceFactory extends AbstractManagedServiceFactory {
	@Autowired
	ConfigurationAdmin configurationAdmin;
	
	private String factoryPid;
	
	public void init() {
		try {
			final Dictionary<String, Object> properties = new Hashtable<String, Object>();		
			final Configuration configuration = configurationAdmin.createFactoryConfiguration(getFactoryPid());
			properties.put("key", "value");
			configuration.update(properties);
			logger.info(configuration);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected void createService(String pid, Dictionary<String, ?> properties) {
		logger.info(MessageFormat.format("[{0}][{1}]", pid, properties));
		
	}

	@Override
	protected void updateService(String pid, Dictionary<String, ?> properties) {
		logger.info(MessageFormat.format("[{0}][{1}]", pid, properties));
		
	}

	@Override
	protected void deleteService(String pid) {
		logger.info(MessageFormat.format("[{0}]", pid));		
	}

	
	public String getFactoryPid() {
		return factoryPid;
	}

	@Required
	public void setFactoryPid(String factoryPid) {
		this.factoryPid = factoryPid;
	}

}
