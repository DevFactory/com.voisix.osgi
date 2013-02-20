package com.voisix.osgi.examples.quartz;

import static org.quartz.JobBuilder.newJob;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;

public class JobDetailFactoryBean implements FactoryBean<JobDetail> {
	
	private Class<Job> jobClass;

	@Override
	public JobDetail getObject() throws Exception {
		return newJob(jobClass)
					.withIdentity("jobName", "jobGroup") 							
					.withDescription("jobDescription")					
					.build();
	}

	@Override
	public Class<?> getObjectType() {		
		return JobDetail.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public Class<Job> getJobClass() {
		return jobClass;
	}

	@Required
	public void setJobClass(Class<Job> jobClass) {
		this.jobClass = jobClass;
	}
}
