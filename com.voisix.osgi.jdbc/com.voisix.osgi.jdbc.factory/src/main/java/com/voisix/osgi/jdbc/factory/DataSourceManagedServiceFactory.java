package com.voisix.osgi.jdbc.factory;

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.cm.ConfigurationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;

import com.voisix.osgi.common.AbstractManagedServiceFactory;

public class DataSourceManagedServiceFactory extends AbstractManagedServiceFactory<DataSource> {
		
	private static final String FACTORY_CLASS 	= "factoryClass";
	private static final String DRIVER_CLASS	= "driverClass";
	private static final String SPRING_JDBC_FACTORY_CLASS_NAME = "org.springframework.jdbc.datasource.SimpleDriverDataSource";
	
	public DataSourceManagedServiceFactory(List<String> serviceInterfaces) {
		super(serviceInterfaces);
	}
		    
	@Override
	protected final DataSource createService(String pid, Dictionary<String, ?> properties) throws ConfigurationException {
		final String factoryClassName		= (String) properties.get(FACTORY_CLASS);
		try {
			final Class<?> factory			= Class.forName(factoryClassName);
			final DataSource dataSource 	= (DataSource) BeanUtils.instantiate(factory);
			final BeanWrapper beanWrapper 	= PropertyAccessorFactory.forBeanPropertyAccess(dataSource);
			final MutablePropertyValues propertyValues = getPropertyValues(properties);
			
			if (SPRING_JDBC_FACTORY_CLASS_NAME.equals(factoryClassName)) {	
				final String drvierClassName 	= (String) properties.get(DRIVER_CLASS);
				try {
					final Class<?> driverClass 		= Class.forName(drvierClassName);
					propertyValues.getPropertyValue(DRIVER_CLASS).setConvertedValue(driverClass);
				} catch (ClassNotFoundException e) {
					throw new ConfigurationException(DRIVER_CLASS, e.getMessage(), e);			
				}
			}				
			beanWrapper.setPropertyValues(propertyValues, true);
			return dataSource;
		} catch (ClassNotFoundException e) {
			throw new ConfigurationException(FACTORY_CLASS, e.getMessage(), e);			
		}
	}
	
	@Override
	protected final void updateService(DataSource dataSource, Dictionary<String, ?> properties) throws ConfigurationException {				
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
				throw new ConfigurationException(DRIVER_CLASS, e.getMessage(), e);	
			}
		}		
		beanWrapper.setPropertyValues(propertyValues, true);			
	}		
	
	@Override
	protected void deleteService(DataSource dataSoruce) {		
	}
}
