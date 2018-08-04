package com.example.quartzdemo.jobs;

import com.example.quartzdemo.services.SimpleHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//SpringBoot自带的简单定时任务执行
@Component
public class StaticJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static long SECOND = 1000;

    @Autowired
    SimpleHelloService simpleHelloService;

//    固定每隔10s执行一次
    @Scheduled(fixedRate = 10 * SECOND)
    public void fixedRateJob() {
        logger.info("{}", simpleHelloService.hello("Spring Boot: fixedRateJob"));
    }

//    固定延迟10s执行一次
    @Scheduled(fixedDelay = 10 * SECOND)
    public void fixedDelayJob() {
        logger.info("{}", simpleHelloService.hello("Spring Boot: fixedDelayJob"));
    }

//    crontab方式定义更灵活的定时方式
    @Scheduled(cron = "*/10 * * * * *")
    public void cronJob() {
        logger.info("{}", simpleHelloService.hello("Spring Boot: cronJob"));
    }
}
