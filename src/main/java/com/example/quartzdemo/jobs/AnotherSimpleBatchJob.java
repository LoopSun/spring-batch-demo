package com.example.quartzdemo.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class AnotherSimpleBatchJob implements Tasklet {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        logger.info("AnotherSimpleBatchJob Executed");

        return RepeatStatus.FINISHED;
    }
}
