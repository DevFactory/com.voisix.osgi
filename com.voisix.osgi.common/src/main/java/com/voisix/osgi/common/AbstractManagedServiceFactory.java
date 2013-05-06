package com.voisix.osgi.common;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.osgi.context.BundleContextAware;

public abstract class AbstractManagedServiceFactory<T> implements BundleContextAware, ManagedServiceFactory {

	private final Map<String, ServiceRegistration<T>> serviceRegistrationMap = new HashMap<String, ServiceRegistration<T>>(2);	
    private BundleContext bundleContext;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    final List<String> serviceInterfaces;
    
    public AbstractManagedServiceFactory(List<String> serviceInterfaces) {
		this.serviceInterfaces = serviceInterfaces;
	}
    
	@Override
	public String getName() {		
		return getClass().getName();
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties) throws ConfigurationException {
		if (serviceRegistrationMap.containsKey(pid)) {
			final ServiceRegistration<T> serviceRegistration = serviceRegistrationMap.get(pid);
			final ServiceReference<T> serviceReference = serviceRegistration.getReference();			
			final T service = bundleContext.getService(serviceReference);
			updateService(service, properties);	
			serviceRegistration.setProperties(properties);			
			logger.info("Updated: " + service.getClass().getName() + properties);	
		} else {
			final T service = createService(pid, properties);		
			@SuppressWarnings("unchecked")
			ServiceRegistration<T> serviceRegistration = (ServiceRegistration<T>) 			
				bundleContext.registerService(serviceInterfaces.toArray(new String[] {}), service, properties);			
			serviceRegistrationMap.put(pid, serviceRegistration);
			logger.info("Created: " + service.getClass().getName() + properties);			
		}
	}

	@Override
	public void deleted(String pid) {
		final ServiceRegistration<T> serviceRegistration = serviceRegistrationMap.get(pid);
		final ServiceReference<T> serviceReference = serviceRegistration.getReference();			
		final T service = bundleContext.getService(serviceReference);
		final String className = service.getClass().getName();
		serviceRegistration.unregister();
		serviceRegistrationMap.remove(pid);
		deleteService(service);
		logger.info("Deleted: " + className + "{pid=" + pid + "}");
	}
	
	@Override
	public final void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	protected final MutablePropertyValues getPropertyValues(Dictionary<String, ?> properties) {
		final MutablePropertyValues propertyValues = new MutablePropertyValues();
		for (final Enumeration<String> e = properties.keys(); e.hasMoreElements(); ) {
			final String key = e.nextElement();
			propertyValues.addPropertyValue(key, properties.get(key));
		}
		return propertyValues;
	}

	protected final T getService(String pid) {
		final ServiceRegistration<T> serviceRegistration = serviceRegistrationMap.get(pid);
		final ServiceReference<T> serviceReference = serviceRegistration.getReference();			
		final T service = bundleContext.getService(serviceReference);
		return service;
	}
	
	protected abstract T 	createService(String pid, Dictionary<String, ?> properties) throws ConfigurationException;
	protected abstract void updateService(T service, Dictionary<String, ?> properties) throws ConfigurationException;
	protected abstract void deleteService(T service);
	
}
