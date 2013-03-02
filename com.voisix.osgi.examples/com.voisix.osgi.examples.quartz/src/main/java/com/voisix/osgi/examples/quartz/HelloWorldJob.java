package com.voisix.osgi.examples.quartz;

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

public class HelloWorldJob implements org.quartz.Job {
	private final Log logger = LogFactory.getLog(HelloWorldJob.class);
	private final JobParametersIncrementer jobParametersIncrementer = new RunIdIncrementer();
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			final SchedulerContext schedulerContext = context.getScheduler().getContext();
			final BundleContext schedulerBundleContext = (BundleContext) schedulerContext.get("bundleContext");
			final String bundleSymbilicName = schedulerBundleContext.getBundle().getHeaders().get(Constants.BUNDLE_SYMBOLICNAME);
			logger.info(bundleSymbilicName);
						
			try {
				final ServiceReference<JobLauncher> jobLauncherReference = schedulerBundleContext.getServiceReference(JobLauncher.class);
				final Collection<ServiceReference<org.springframework.batch.core.Job>> jobReferences = 
						schedulerBundleContext.getServiceReferences(Job.class,
						"(& (Bundle-SymbolicName=com.voisix.osgi.examples.batch.job.helloworld) (org.springframework.osgi.bean.name=helloWorldJob))");				
				final JobLauncher jobLauncher = schedulerBundleContext.getService(jobLauncherReference);
				if (jobLauncherReference!=null) {
					for (ServiceReference<Job> batchJobReference : jobReferences) {
						final Job batchJob = schedulerBundleContext.getService(batchJobReference);
						jobLauncher.run(batchJob, jobParametersIncrementer.getNext(new JobParameters()));
					}
				}
			} catch (InvalidSyntaxException e) {
				throw new SchedulerException(e);
			} catch (JobExecutionAlreadyRunningException e) {
				throw new SchedulerException(e);
			} catch (JobRestartException e) {
				throw new SchedulerException(e);
			} catch (JobInstanceAlreadyCompleteException e) {
				throw new SchedulerException(e);
			} catch (JobParametersInvalidException e) {
				throw new SchedulerException(e);
			}			
		} catch (SchedulerException e) {
			throw new JobExecutionException(e);
		}
	}	
}
