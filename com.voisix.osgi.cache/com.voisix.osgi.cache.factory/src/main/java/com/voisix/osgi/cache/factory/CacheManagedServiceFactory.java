package com.voisix.osgi.cache.factory;

import java.util.Dictionary;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;

import org.osgi.service.cm.ConfigurationException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.voisix.osgi.common.AbstractManagedServiceFactory;


public final class CacheManagedServiceFactory extends AbstractManagedServiceFactory<Ehcache> {

	private EhCacheCacheManager ehCacheManager;

	public CacheManagedServiceFactory(List<String> serviceInterfaces) {
		super(serviceInterfaces);
	}
	
	private CacheConfiguration createCacheConfiguration(Dictionary<String, ?> properties) {
		final CacheConfiguration cacheConfiguration = new CacheConfiguration();
		final BeanWrapper beanWrapper 	= PropertyAccessorFactory.forBeanPropertyAccess(cacheConfiguration);
		final MutablePropertyValues propertyValues = getPropertyValues(properties);
		beanWrapper.setPropertyValues(propertyValues, true);
		return cacheConfiguration;
	}

	@Override
	protected Ehcache createService(String pid, Dictionary<String, ?> properties)  {
		final CacheManager cacheManager = getEhCacheManager().getCacheManager();
		final CacheConfiguration cacheConfiguration = createCacheConfiguration(properties);		
		final Cache cache = new Cache(cacheConfiguration);
		cache.setCacheManager(cacheManager);
		cacheManager.addCacheIfAbsent(cache);		
		getEhCacheManager().afterPropertiesSet();
		return cache;
	}

	@Override
	protected void updateService(Ehcache cache, Dictionary<String, ?> properties) throws ConfigurationException {
		final CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
		final BeanWrapper beanWrapper 	= PropertyAccessorFactory.forBeanPropertyAccess(cacheConfiguration);
		final MutablePropertyValues propertyValues = getPropertyValues(properties);
		beanWrapper.setPropertyValues(propertyValues, true);			
		getEhCacheManager().afterPropertiesSet();
		logger.info("Updated: " + cache);			
	}
	
	@Override
	protected void deleteService(Ehcache cache) {
		final CacheManager cacheManager = ehCacheManager.getCacheManager();		
		cacheManager.removeCache(cache.getName());
		getEhCacheManager().getCacheNames().remove(cache.getName());
		getEhCacheManager().afterPropertiesSet();
	}

	public EhCacheCacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	@Required
	public void setEhCacheManager(EhCacheCacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}	
}