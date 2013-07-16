
package com.voisix.osgi.cache.commands;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.springframework.cache.CacheManager;

@Command(scope = "cache", name = "cache-list", description = "List Cache Names")
public class ListCacheNames extends OsgiCommandSupport {

    private CacheManager cacheManager; 
    
    private final Log logger = LogFactory.getLog(getClass());

    protected Object doExecute() {
    	logger.info(cacheManager.getClass().getName());      	
    	for (String name : cacheManager.getCacheNames()) {
    		logger.info(name);
    	}
        return cacheManager.getCacheNames();         
    }

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}
