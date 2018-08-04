package com.example.quartzdemo.services;


import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class QuartzJobManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Scheduler scheduler;


    // 定义添加任务
    @SuppressWarnings({"unchecked"})
    public Date addJob(String jobName, String jobGroupName, String triggerName,
                       String triggerGroupName, Class jobClass, String cronExpression) {


        //定义Job
        JobDetail jobDetail = JobBuilder
                .newJob(jobClass)
                .withIdentity(jobName, jobGroupName)
                .build();

        //定义Trigger
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        try {
            return scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    //修改任务
    public void modifyJob(String jobName, String jobGroupName, String triggerName,
                          String triggerGroupName, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cronExpression)) {
                // 重新分配时间给任务
                Trigger trigger1 = TriggerBuilder
                        .newTrigger()
                        .withIdentity(triggerName, triggerGroupName)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .build();
                scheduler.rescheduleJob(triggerKey, trigger);

                // 删除任务重建
//                JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
//                Class<? extends Job> jobClass = jobDetail.getJobClass();
//                removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
//                addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobDetail.getJobClass(), cronExpression);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //删除任务
    public void removeJob(String jobName, String jobGroupName, String triggerName,
                          String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            // 暂停触发器
            scheduler.pauseTrigger(triggerKey);
            //　删除触发器
            scheduler.unscheduleJob(triggerKey);
            //　删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //暂停任务
    public void pauseJob(String jobName, String jobGroupName, String triggerName,
                          String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseTrigger(triggerKey);
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //恢复任务
    public void resumeJob(String jobName, String jobGroupName, String triggerName,
                           String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeTrigger(triggerKey);
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //启动所有任务
    public void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //关闭所有任务
    public void shutdownJObs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //暂停所有任务
    public void  pauseJobs() {
        try {
            scheduler.pauseAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //恢复所有任务
    public void resumeJobs() {
        try {
            scheduler.resumeAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
