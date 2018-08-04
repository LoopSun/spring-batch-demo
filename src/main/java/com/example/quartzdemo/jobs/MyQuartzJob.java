package com.example.quartzdemo.jobs;

import com.example.quartzdemo.services.QuartzJobManager;
import com.example.quartzdemo.services.SimpleHelloService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;


//定义一个简单Quartz的任务
public class MyQuartzJob extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private String name;

    @Autowired
    SimpleHelloService simpleHelloService;

    @Autowired
    QuartzJobManager quartzJobManager;

    //接受JobDetail传递参数
    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("{} " + this.name, dateFormat.format(new Date()));
//        logger.info("{}", simpleHelloService.hello("QuartzJob"));
//        quartzJobManager.addJob(
//                        "myjob2",
//                        "myjob",
//                        "myjob2Trigger",
//                        "myjobTrigger",
//                        MyQuartzJob.class,
//                        "0/3 * * * * ?"
//                );
//        quartzJobManager.modifyJob(
//                "myjob2",
//                "myjob",
//                "myjob2Trigger",
//                "myjobTrigger",
//                "0/20 * * * * ?");
//
//    }
            quartzJobManager.removeJob(
                    "myjob2",
                    "myjob",
                    "myjob2Trigger",
                    "myjobTrigger");

}
}
