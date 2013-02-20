package com.voisix.osgi.quartz.core;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.core.InfrastructureProxy;

public class SchedulerDecorator {
	private final static Log logger = LogFactory.getLog(SchedulerDecorator.class);
	private Scheduler scheduler;
	
	public final void scheduleJob(JobDetail jobDetail,  Map<String, Object> properties) throws SchedulerException {		
		scheduler.addJob(jobDetail, true);
		logger.info("addJob: " + jobDetail);
	}
	
	public final void unscheduleJob(JobDetail jobDetail,  Map<String, Object> properties) throws SchedulerException {		
		scheduler.deleteJob(jobDetail.getKey());
		logger.info("deleteJob: " + jobDetail);
	}
	
	public final void scheduleJob(Trigger trigger, Map<String, Object> properties) throws SchedulerException {				
		final InfrastructureProxy proxy = (InfrastructureProxy) trigger;
		final Trigger target = (Trigger) proxy.getWrappedObject();	
		scheduler.scheduleJob(target);
		logger.info("scheduleJob: " + trigger);		
	}
	
	public final void unscheduleJob(Trigger trigger, Map<String, Object> properties) throws SchedulerException {			
		scheduler.unscheduleJob(trigger.getKey());
		logger.info("unscheduleJob: " + trigger);	
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
}
