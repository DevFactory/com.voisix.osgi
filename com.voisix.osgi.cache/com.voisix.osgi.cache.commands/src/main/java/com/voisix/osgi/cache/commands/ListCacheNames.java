
package com.voisix.osgi.cache.commands;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.springframework.cache.CacheManager;

@Command(scope = "cache", name = "list", description = "List Cache Names")
public class ListCacheNames extends OsgiCommandSupport {

//    @Option(name = "-o", aliases = { "--option" }, description = "An option to the command", required = false, multiValued = false)
//    private String option;
//
//    @Argument(name = "argument", description = "Argument to the command", required = false, multiValued = false)
//    private String argument;
    
    private CacheManager cacheManager; 
    
    private final Log logger = LogFactory.getLog(getClass());

    protected Object doExecute() throws Exception {
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
