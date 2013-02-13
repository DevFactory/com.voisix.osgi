package com.voisix.osgi.examples.batch.job.helloworld;

import java.util.logging.Logger;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class HelloWorldTasklet implements Tasklet {
	private final static Logger logger = Logger.getLogger(HelloWorldTasklet.class.getName());

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("Hello World Tasklet");
		 return RepeatStatus.FINISHED;
	}

}
