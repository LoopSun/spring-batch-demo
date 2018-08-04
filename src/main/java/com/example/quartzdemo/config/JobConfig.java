package com.example.quartzdemo.config;

import com.example.quartzdemo.jobs.MyQuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// 配置任务运行配置
@Configuration
public class JobConfig {


    // 配置JobDetail类
    @Bean
    public JobDetail myJobDetail() {
        return JobBuilder
                .newJob(MyQuartzJob.class)
                .withIdentity("MyQuartzJob")
                .storeDurably()
                .build();
    }


    // 配置Quartz Trigger
    @Bean
    public Trigger myJobTrigger() {
        //配置定时(simple)
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(2)
                .repeatForever();

        //配置定时(cron)
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/2 * * * * ?");

        //返回Trigger
        return TriggerBuilder
                .newTrigger()
                .forJob(myJobDetail())
                .withIdentity("myTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
