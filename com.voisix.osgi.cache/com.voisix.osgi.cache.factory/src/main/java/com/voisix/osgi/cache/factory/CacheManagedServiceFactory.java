package com.voisix.osgi.cache.factory;

import java.util.Dictionary;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.voisix.osgi.common.AbstractManagedServiceFactory;


public class CacheManagedServiceFactory extends AbstractManagedServiceFactory {

	private EhCacheCacheManager ehCacheManager;

	@Override
	protected void createService(String pid, Dictionary<String, ?> properties) {		
		logger.info("Created: " + ehCacheManager.getClass().getName() + properties);
	}

	@Override
	protected void updateService(String pid, Dictionary<String, ?> properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void deleteService(String pid) {
		// TODO Auto-generated method stub
		
	}

	public EhCacheCacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	@Required
	public void setEhCacheManager(EhCacheCacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}	
}