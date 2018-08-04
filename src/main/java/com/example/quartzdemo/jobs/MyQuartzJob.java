package com.example.quartzdemo.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;


//定义一个简单Quartz的任务
public class MyQuartzJob extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Quartz: ******{}******", dateFormat.format(new Date()));
    }
}
