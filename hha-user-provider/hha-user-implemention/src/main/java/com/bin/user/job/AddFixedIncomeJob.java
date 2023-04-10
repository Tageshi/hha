package com.bin.user.job;

import com.bin.user.dao.IncomeInfoDao;
import com.bin.user.pojo.DTO.AddIncomeRecordDTO;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/11 2:29
 */
public class AddFixedIncomeJob implements Job {
    @Autowired
    private IncomeInfoDao incomeInfoDao;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        AddIncomeRecordDTO addIncomeRecordDTO = (AddIncomeRecordDTO) jobDataMap.get("addIncomeRecordDTO");
        Long userId = (Long) jobDataMap.get("userId");
        incomeInfoDao.insertIncomeRecord(addIncomeRecordDTO.getTypeId(),addIncomeRecordDTO.getIncomeName(),
                addIncomeRecordDTO.getIncome(),addIncomeRecordDTO.getDate(),userId);
    }
}
