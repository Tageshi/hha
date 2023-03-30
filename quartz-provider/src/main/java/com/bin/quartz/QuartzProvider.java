package com.bin.quartz;

import com.bin.quartz.job.AddRoutinedOutcomeJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author tageshi
 * @date 2023/3/28 20:53
 */
public class QuartzProvider {
    private String cron;
    public String getCron() {
        return cron;
    }
    public void setCron(String cron) {
        this.cron = cron;
    }

    public boolean addQuartz(){
        try {
            //获取一个调度工厂
            SchedulerFactory schedulerFact = new StdSchedulerFactory();
            //  从工厂里面拿到一个scheduler实例
            Scheduler scheduler = schedulerFact.getScheduler();
            // 真正执行的任务并不是Job接口的实例，而是用反射的方式实例化的一个JobDetail实例
            JobDetail job = JobBuilder.newJob(AddRoutinedOutcomeJob.class)
                    // 根据name和默认的group(即"DEFAULT_GROUP")创建trigger的key
                    .withIdentity("job", "group")
                    //创建触发器
                    .build();
            //SimpleTrigger-简单触发器
            Date startTime = DateBuilder.nextGivenSecondDate(new Date( ),5);  // 在当前时间5秒后运行
            //CronTrigger-Cron触发器
            //按照时间间隔顺序执行
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").withSchedule(
                    CronScheduleBuilder.cronSchedule(cron)
            ).build();
            scheduler.scheduleJob(job, trigger);
            // 调度启动
            scheduler.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
