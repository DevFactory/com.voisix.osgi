package com.voisix.osgi.batch.core;

import java.util.Map;

import org.springframework.batch.core.Job;

public interface OSGIServiceJobRegistrar {
	void register(Job job, Map<String, Object> properties);
	void unregister(Job job, Map<String, Object> properties);
}
