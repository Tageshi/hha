package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bin.user.api.OutcomeService;
import com.bin.user.api.QuartzService;
import com.bin.user.dao.OutcomeInfoDao;
import com.bin.user.job.AddFixedIncomeJob;
import com.bin.user.job.AddRoutinedOutcomeJob;
import com.bin.user.pojo.DTO.AddIncomeRecordDTO;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.VO.RoutineItemVO;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/11 0:35
 */
@Service
public class QuartzServiceImpl implements QuartzService {
    private final OutcomeService outcomeService;
    private final Scheduler scheduler;

    @Autowired
    public QuartzServiceImpl(OutcomeService outcomeService, Scheduler scheduler) {
        this.outcomeService = outcomeService;
        this.scheduler = scheduler;
    }
    @Autowired
    private OutcomeInfoDao outcomeInfoDao;

    @Override
    public boolean scheduleAddRoutinedOutcomeJob(String cronExpression, String startTime,
                                                 String endTime,Long routineId,Long userId) throws ParseException {
        //获取消费路线对应信息
        List<AddOutcomeRecordDTO> routinedOutcomeRecords = new ArrayList<>();
        List<RoutineItemVO> details = outcomeInfoDao.getRoutineItem(routineId);
        for (RoutineItemVO item:details) {
            AddOutcomeRecordDTO addOutcomeRecordDTO = new AddOutcomeRecordDTO();
            addOutcomeRecordDTO.setTypeId(item.getRoutineDetailTypeId());
            addOutcomeRecordDTO.setOutcomeName(item.getRoutineDetailName());
            addOutcomeRecordDTO.setOutcome(item.getRoutineDetailCost());
            addOutcomeRecordDTO.setDate(new Date());
        }

        // 封装需要传递的数据到JobDataMap中
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("routinedOutcomeRecords", routinedOutcomeRecords);
        jobDataMap.put("userId", userId);

        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(AddRoutinedOutcomeJob.class)
                .withIdentity("accountingJob", "accounting")
                .usingJobData(jobDataMap) // 将JobDataMap与jobDetail相关联
                .build();

        // 创建触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("accountingTrigger", "accounting")
                .startAt(DateUtils.parseDate(startTime)) // 设置起始时间
                .endAt(DateUtils.parseDate(endTime)) // 设置终止时间
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        try {
            // 调度任务
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @description: 添加固定收入
     * @author: tageshi
     * @date: 2023/4/11 2:29
     **/
    @Override
    public boolean scheduleAddFixedIncomeJob(String cronExpression, AddIncomeRecordDTO addIncomeRecordDTO, Long userId) {
        // 封装需要传递的数据到JobDataMap中
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("addIncomeRecordDTO", addIncomeRecordDTO);
        jobDataMap.put("userId", userId);

        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(AddFixedIncomeJob.class)
                .withIdentity("accountingJob", "accounting")
                .usingJobData(jobDataMap) // 将JobDataMap与jobDetail相关联
                .build();

        // 创建触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("accountingTrigger", "accounting")
                .startAt(new Date()) // 设置起始时间为现在
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        try {
            // 调度任务
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


}

