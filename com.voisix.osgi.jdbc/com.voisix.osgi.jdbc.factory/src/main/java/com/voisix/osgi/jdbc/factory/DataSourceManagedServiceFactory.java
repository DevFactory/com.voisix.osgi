package com.voisix.osgi.jdbc.factory;

import java.util.Dictionary;

import org.apache.aries.blueprint.annotation.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;

public class DataSourceManagedServiceFactory implements ManagedServiceFactory {
	final Log logger = LogFactory.getLog(getClass());
	
	@Inject(ref="blueprintBundleContext")
    private BundleContext context;

	@Override
	public String getName() {
		return "DataSource Managed Service Factory";
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties) throws ConfigurationException {
		logger.info("Updated: " + properties);		
	}

	@Override
	public void deleted(String pid) {
		logger.info("Deleted: " + pid);
	}

}
