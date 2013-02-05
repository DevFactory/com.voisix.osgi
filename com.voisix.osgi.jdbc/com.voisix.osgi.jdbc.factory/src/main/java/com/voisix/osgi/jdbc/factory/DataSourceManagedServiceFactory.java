package com.voisix.osgi.jdbc.factory;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;

import com.voisix.osgi.common.AbstractManagedServiceFactory;

public class DataSourceManagedServiceFactory extends AbstractManagedServiceFactory {
	
	private final static String FACTORY_CLASS 	= "factoryClass";
	private final static String DRIVER_CLASS	= "driverClass";
	private final static String SPRING_JDBC_FACTORY_CLASS_NAME = "org.springframework.jdbc.datasource.SimpleDriverDataSource";
		
	private final Log logger = LogFactory.getLog(getClass());
	private final Map<String, ServiceRegistration<DataSource>> serviceRegistrationMap = new HashMap<String, ServiceRegistration<DataSource>>(2);
	
    
	@Override
	protected final void createService(String pid, Dictionary<String, ?> properties) {
		try {
			final String factoryClassName	= (String) properties.get(FACTORY_CLASS);
			final Class<?> factory			= Class.forName(factoryClassName);
			final DataSource dataSource 	= (DataSource) BeanUtils.instantiate(factory);
			final BeanWrapper beanWrapper 	= PropertyAccessorFactory.forBeanPropertyAccess(dataSource);
			final MutablePropertyValues propertyValues = getPropertyValues(properties);
			
			if (SPRING_JDBC_FACTORY_CLASS_NAME.equals(factoryClassName)) {	
				final String drvierClassName 	= (String) properties.get(DRIVER_CLASS);
				final Class<?> driverClass 		= Class.forName(drvierClassName);
				propertyValues.getPropertyValue(DRIVER_CLASS).setConvertedValue(driverClass);
			}
			
			beanWrapper.setPropertyValues(propertyValues, true);
			ServiceRegistration<DataSource> serviceRegistration = bundleContext.registerService(DataSource.class, dataSource, properties);
			serviceRegistrationMap.put(pid, serviceRegistration);
			logger.info("Created: " + dataSource.getClass().getName() + properties);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	protected final void updateService(String pid, Dictionary<String, ?> properties) {		
		final ServiceRegistration<DataSource> serviceRegistration = serviceRegistrationMap.get(pid);
		final ServiceReference<DataSource> serviceReference = serviceRegistration.getReference();
		final DataSource dataSource 	= bundleContext.getService(serviceReference);			
		final BeanWrapper beanWrapper 	= PropertyAccessorFactory.forBeanPropertyAccess(dataSource);
		final MutablePropertyValues propertyValues = new MutablePropertyValues();
		for (String key : Collections.list(properties.keys())) {				
			propertyValues.addPropertyValue(key, properties.get(key));				
		}
				
		if (SPRING_JDBC_FACTORY_CLASS_NAME.equals(dataSource.getClass().getName())) {
			try {
				final String drvierClassName 	= (String) properties.get(DRIVER_CLASS);
				final Class<?> driverClass 		= Class.forName(drvierClassName);
				propertyValues.getPropertyValue(DRIVER_CLASS).setConvertedValue(driverClass);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		
		beanWrapper.setPropertyValues(propertyValues, true);
		serviceRegistration.setProperties(properties);
		logger.info("Updated: " + dataSource.getClass().getName() + properties);		
	}
	
	@Override
	protected final void deleteService(String pid) {
		final ServiceRegistration<DataSource> serviceRegistration = serviceRegistrationMap.get(pid);
		final ServiceReference<DataSource> serviceReference = serviceRegistration.getReference();			
		final DataSource dataSource = bundleContext.getService(serviceReference);
		final String className = dataSource.getClass().getName();
		serviceRegistration.unregister();
		serviceRegistrationMap.remove(pid);
		logger.info("Deleted: " + className + "{pid=" + pid + "}");
	}
}
