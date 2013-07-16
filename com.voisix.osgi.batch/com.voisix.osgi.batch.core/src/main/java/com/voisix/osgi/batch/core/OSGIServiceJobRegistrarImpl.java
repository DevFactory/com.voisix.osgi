package com.voisix.osgi.batch.core;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;

public class OSGIServiceJobRegistrarImpl implements OSGIServiceJobRegistrar {
	
	private final Logger logger = Logger.getLogger(OSGIServiceJobRegistrar.class.getName());
	
	private JobRegistry jobRegistry;
	
	@Override
	public void register(Job job, Map<String, Object> properties) {
		JobFactory jobFactory = new ReferenceJobFactory(job);
		try {
			jobRegistry.register(jobFactory);
			logger.info("Registered: " + job);
		} catch (DuplicateJobException e) {
			logger.warning(e.getMessage());
		}
		
	}

	@Override
	public void unregister(Job job, Map<String, Object> properties) {
		jobRegistry.unregister(job.getName());
		logger.info("Unregistered: " + job);		
	}

	public JobRegistry getJobRegistry() {
		return jobRegistry;
	}

	public void setJobRegistry(JobRegistry jobRegistry) {
		this.jobRegistry = jobRegistry;
	}

}
