package com.voisix.osgi.cache.factory;

import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.cache.ehcache.EhCacheCacheManager;


public class CustomEhCacheCacheManager extends EhCacheCacheManager {
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getCacheNames() {
		try {
			Class<?> abstractCacheManagerClass = this.getClass().getSuperclass().getSuperclass();			
			Field field = abstractCacheManagerClass.getDeclaredField("cacheNames");
			field.setAccessible(true);
			return (Collection<String>) field.get(this);
		
		} catch (SecurityException e) {		
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();		
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return super.getCacheNames();
	}
}
