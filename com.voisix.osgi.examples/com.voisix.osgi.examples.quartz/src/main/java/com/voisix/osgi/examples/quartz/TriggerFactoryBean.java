package com.voisix.osgi.examples.quartz;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.osgi.context.BundleContextAware;

public class TriggerFactoryBean implements FactoryBean<Trigger>, BeanNameAware, BundleContextAware {
	
	private JobDetail jobDetail;
	private String name;
	private BundleContext bundleContext;
	
	@Override
	public Trigger getObject() throws Exception {
		return newTrigger()
			.withIdentity(name, bundleContext.getProperty(Constants.BUNDLE_NAME))
			.withDescription(bundleContext.getProperty(Constants.BUNDLE_DESCRIPTION))
			.startNow()
			.withSchedule(simpleSchedule().withIntervalInSeconds(30).repeatForever())
			.forJob(getJobDetail())
			.build();	
	}

	@Override
	public Class<?> getObjectType() {
		return Trigger.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	@Override
	public void setBeanName(String name) {
		this.name=name;		
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}
}
