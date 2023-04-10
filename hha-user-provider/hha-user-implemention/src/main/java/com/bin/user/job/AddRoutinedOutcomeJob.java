package com.bin.user.job;

import com.bin.user.dao.OutcomeInfoDao;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/11 0:39
 */
public class AddRoutinedOutcomeJob implements Job {

    @Autowired
    private OutcomeInfoDao outcomeInfoDao;

    public AddRoutinedOutcomeJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        List<AddOutcomeRecordDTO> routinedOutcomeRecords = (List<AddOutcomeRecordDTO>) jobDataMap.get("routinedOutcomeRecords");
        Long userId = (Long) jobDataMap.get("userId");

        //插入数据
        for (AddOutcomeRecordDTO record:routinedOutcomeRecords) {
            outcomeInfoDao.insertOutcomeRecord(record.getTypeId(),record.getOutcomeName(),
                    record.getOutcome(),record.getDate(),userId);
        }

    }
}

