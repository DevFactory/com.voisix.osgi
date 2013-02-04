package com.voisix.osgi.common;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.springframework.osgi.context.BundleContextAware;

public abstract class AbstractManagedServiceFactory implements BundleContextAware, ManagedServiceFactory {
	
	protected final Log logger = LogFactory.getLog(getClass());
	protected final Map<String, ServiceRegistration<DataSource>> serviceRegistrationMap = new HashMap<String, ServiceRegistration<DataSource>>(2);
	
    protected BundleContext bundleContext;

	@Override
	public String getName() {		
		return getClass().getName();
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties) throws ConfigurationException {
		if (serviceRegistrationMap.containsKey(pid)) {
			updateService(pid, properties);			
		} else {
			createService(pid, properties);
		}
	}

	@Override
	public void deleted(String pid) {
		deleteService(pid);
	}
	
	@Override
	public final void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	protected abstract void createService(String pid, Dictionary<String, ?> properties);	
	protected abstract void updateService(String pid, Dictionary<String, ?> properties);
	protected abstract void deleteService(String pid);

}
