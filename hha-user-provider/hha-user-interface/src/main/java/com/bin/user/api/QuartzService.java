package com.bin.user.api;

import com.bin.user.pojo.DTO.AddIncomeRecordDTO;
import com.bin.user.pojo.PO.MyPeriod;

import java.text.ParseException;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/4/11 0:32
 */
public interface QuartzService {
    boolean scheduleAddRoutinedOutcomeJob(String cronExpression,String startTime,
                                          String endTime,Long routineId,Long userId) throws ParseException;
    boolean scheduleAddFixedIncomeJob(String cronExpression, AddIncomeRecordDTO addIncomeRecordDTO,Long userId);
}

