package com.voisix.osgi.examples.batch.job.helloworld;

import java.util.logging.Logger;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

public class HelloWorldTasklet implements Tasklet {
	private final static Logger logger = Logger.getLogger(HelloWorldTasklet.class.getName());
	
	@Value("#{jobParameters['message']}") 
	private String message;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		logger.info("Hello World Tasklet");
		logger.info(message);
		return RepeatStatus.FINISHED;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
