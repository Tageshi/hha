package com.bin.quartz.job;

import com.bin.user.api.OutcomeService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tageshi
 * @date 2023/3/28 20:54
 */
public class AddRoutinedOutcomeJob implements Job {
    @Autowired
    private OutcomeService outcomeService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行添加固定路线定时任务");
        //将dto保存到RedisCache中，随后读取对应的Redis数值
        /*outcomeService.addRoutinedOutcome()*/
    }
}