package com.voisix.osgi.jdbc.factory;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.osgi.context.BundleContextAware;

public class DataSourceManagedServiceFactory implements ManagedServiceFactory, BundleContextAware {
	
	private final static String FACTORY_CLASS 	= "factoryClass";
		
	private final Log logger = LogFactory.getLog(getClass());
	private final Map<String, ServiceRegistration<DataSource>> serviceRegistrationMap = new HashMap<String, ServiceRegistration<DataSource>>(2);
	
    private BundleContext context;

	@Override
	public String getName() {
		return "DataSource Managed Service Factory";
	}
	
	@Override
	public void deleted(String pid) {
		if (serviceRegistrationMap.containsKey(pid)) {
			deleteDataSourceService(pid);
		}		
	}
	
	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.context = bundleContext;		
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties) throws ConfigurationException {
		if (null!=properties.get(FACTORY_CLASS)) {
			try {					
				if (serviceRegistrationMap.containsKey(pid)) {
					updateDataSourceService(pid, properties);
				} else {
					createDataSourceService(pid, properties);
				}
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			logger.warn("DataSource configuration " + pid + " must contain factoryClass property");
		}
	}

	private final DataSource createDataSourceService(String pid, Dictionary<String, ?> properties) throws ClassNotFoundException {
		final String factoryClassName	= (String) properties.get(FACTORY_CLASS);
		final Class<?> factory			= Class.forName(factoryClassName);
		final DataSource dataSource 	= (DataSource) BeanUtils.instantiate(factory);
		final BeanWrapper beanWrapper 	= PropertyAccessorFactory.forBeanPropertyAccess(dataSource);
		final MutablePropertyValues propertyValues = new MutablePropertyValues();
		for (String key : Collections.list(properties.keys())) {				
			propertyValues.addPropertyValue(key, properties.get(key));				
		}
		beanWrapper.setPropertyValues(propertyValues, true);
		ServiceRegistration<DataSource> serviceRegistration = context.registerService(DataSource.class, dataSource, properties);
		serviceRegistrationMap.put(pid, serviceRegistration);
		logger.info("Created: " + dataSource.getClass().getName() + properties);		
		return dataSource;
	}
	
	private final DataSource updateDataSourceService(String pid, Dictionary<String, ?> properties) throws ClassNotFoundException {		
		final ServiceRegistration<DataSource> dataSourceServiceRegistration = serviceRegistrationMap.get(pid);
		final ServiceReference<DataSource> dataSourceServiceReference = dataSourceServiceRegistration.getReference();
		final DataSource dataSource 	= context.getService(dataSourceServiceReference);			
		final BeanWrapper beanWrapper 	= PropertyAccessorFactory.forBeanPropertyAccess(dataSource);
		final MutablePropertyValues propertyValues = new MutablePropertyValues();
		for (String key : Collections.list(properties.keys())) {				
			propertyValues.addPropertyValue(key, properties.get(key));				
		}
		beanWrapper.setPropertyValues(propertyValues, true);		
		logger.info("Updated: " + dataSource.getClass().getName() + properties);
		return dataSource;
	}
	
	private final void deleteDataSourceService(String pid) {
		final ServiceRegistration<DataSource> serviceRegistration = serviceRegistrationMap.get(pid);
		final ServiceReference<DataSource> serviceReference = serviceRegistration.getReference();			
		final DataSource dataSource = context.getService(serviceReference);
		final String className = dataSource.getClass().getName();
		context.ungetService(serviceReference);
		serviceRegistrationMap.remove(pid);
		logger.info("Deleted: " + className + "{pid=" + pid + "}");
	}
}
