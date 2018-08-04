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
                //绑定逻辑体
                .newJob(MyQuartzJob.class)
                //命名任务
                .withIdentity("MyQuartzJob")
                //传递参数给Job
                .usingJobData("name", "QuartzJob")
                //没有Trigger也持久化该任务信息到数据库
                .storeDurably()
                .build();
    }


    // 配置Quartz Trigger
    @Bean
    public Trigger myJobTrigger() {
        //配置定时(simple)
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                //２s间隔运行
                .withIntervalInSeconds(2)
                //运行到天荒地老
                .repeatForever();

        //配置定时(cron)
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                // 注意最后的 "?"
                .cronSchedule("*/2 * * * * ?");

        //返回Trigger
        return TriggerBuilder
                //新建触发器
                .newTrigger()
                //绑定任务
                .forJob(myJobDetail())
                //给触发器起个名
                .withIdentity("myTrigger")
                //绑定一个定时计划
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
