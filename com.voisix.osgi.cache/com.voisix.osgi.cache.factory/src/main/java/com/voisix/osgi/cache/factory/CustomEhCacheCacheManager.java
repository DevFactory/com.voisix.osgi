package com.voisix.osgi.cache.factory;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;


public final class CustomEhCacheCacheManager extends EhCacheCacheManager {
	
	private static final String FIELD_CACHE_NAMES = "cacheNames";
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Override AbstractCacheManager#getCacheNames() to return a modifiable collection
	 * @see org.springframework.cache.support.AbstractCacheManager#getCacheNames()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getCacheNames() {
		try {
			final Class<?> abstractCacheManagerClass = this.getClass().getSuperclass().getSuperclass();			
			final Field field = abstractCacheManagerClass.getDeclaredField(FIELD_CACHE_NAMES);
			field.setAccessible(true);
			return (Collection<String>) field.get(this);		
		} catch (SecurityException e) {		
			logger.error(e.getMessage(), e);
		} catch (NoSuchFieldException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return super.getCacheNames();
	}
}
