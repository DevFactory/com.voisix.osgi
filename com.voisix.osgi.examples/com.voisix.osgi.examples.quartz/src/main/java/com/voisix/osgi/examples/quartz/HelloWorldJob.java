package com.voisix.osgi.examples.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

public class HelloWorldJob implements org.quartz.Job {
	private final Log logger = LogFactory.getLog(HelloWorldJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			final SchedulerContext schedulerContext = context.getScheduler().getContext();
			final BundleContext schedulerBundleContext = (BundleContext) schedulerContext.get("bundleContext");
			final String bundleSymbilicName = schedulerBundleContext.getBundle().getHeaders().get("Bundle-Name");
			logger.info(bundleSymbilicName);
		} catch (SchedulerException e) {
			throw new JobExecutionException(e);
		}
			
		
//		final JobParametersIncrementer jobParametersIncrementer = new RunIdIncrementer();
//		final JobLauncher jobLauncher = (JobLauncher) context.get("jobLauncher");
//		final Job job = (Job) context.get("job");
//		try {
//			jobLauncher.run(job, jobParametersIncrementer.getNext(new JobParameters()));
//		} catch (JobExecutionAlreadyRunningException e) {
//			throw new JobExecutionException(e);
//		} catch (JobRestartException e) {
//			throw new JobExecutionException(e);
//		} catch (JobInstanceAlreadyCompleteException e) {
//			throw new JobExecutionException(e);
//		} catch (JobParametersInvalidException e) {
//			throw new JobExecutionException(e);
//		}
//		
	}	
}
