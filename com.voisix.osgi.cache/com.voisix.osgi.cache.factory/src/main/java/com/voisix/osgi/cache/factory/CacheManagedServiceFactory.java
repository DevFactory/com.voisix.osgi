package com.voisix.osgi.cache.factory;

import java.util.Dictionary;

import javax.sql.DataSource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;

import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.voisix.osgi.common.AbstractManagedServiceFactory;


public class CacheManagedServiceFactory extends AbstractManagedServiceFactory {

	private EhCacheCacheManager ehCacheManager;

	@Override
	protected void createService(String pid, Dictionary<String, ?> properties) {
		final CacheConfiguration cacheConfiguration = new CacheConfiguration();
		final BeanWrapper beanWrapper 	= PropertyAccessorFactory.forBeanPropertyAccess(cacheConfiguration);
		final MutablePropertyValues propertyValues = getPropertyValues(properties);
		beanWrapper.setPropertyValues(propertyValues, true);
		
		final CacheManager cacheManager = ehCacheManager.getCacheManager();
		final Cache cache = new Cache(cacheConfiguration);
		cache.setCacheManager(cacheManager);
		cacheManager.addCacheIfAbsent(cache);
		
		final ServiceRegistration<Ehcache> serviceRegistration = bundleContext.registerService(Ehcache.class, cache, properties);
		serviceRegistrationMap.put(pid, serviceRegistration);
		
		logger.info("Configured caches: " + ehCacheManager.getCacheNames());
		logger.info("Created: " + cache);		
	}

	@Override
	protected void updateService(String pid, Dictionary<String, ?> properties) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void deleteService(String pid) {
		final ServiceRegistration<Ehcache> serviceRegistration = (ServiceRegistration<Ehcache>) serviceRegistrationMap.get(pid);
		final ServiceReference<Ehcache> serviceReference = serviceRegistration.getReference();
		final Ehcache cache = bundleContext.getService(serviceReference);
		final CacheManager cacheManager = ehCacheManager.getCacheManager();
		cacheManager.removeCache(cache.getName());
		serviceRegistrationMap.remove(serviceRegistration);
		serviceRegistration.unregister();
		logger.info("Deleted: " + cache);		
	}

	public EhCacheCacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	@Required
	public void setEhCacheManager(EhCacheCacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}	
}