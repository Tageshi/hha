package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.user.pojo.DTO.AddIncomePlanDTO;
import com.bin.user.pojo.DTO.AddIncomeRecordDTO;
import com.bin.user.pojo.PO.IncomeInfo;
import com.bin.user.pojo.VO.IncomePlanVO;
import com.bin.user.pojo.VO.IncomeRecordVO;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/31 13:42
 */
public interface IncomeService extends IService<IncomeInfo> {
    public boolean addFixedIncome();
    public boolean addIncomeRecord(AddIncomeRecordDTO addIncomeRecordDTO,Long userId);
    public boolean updateIncomeRecord(AddIncomeRecordDTO addIncomeRecordDTO,Long incomeId);
    public boolean deleteIncomeRecord(Long incomeId);
    public List<IncomeRecordVO> getIncomeList(Date startTime, Date endTime, Long typeId,Long userId);
    public boolean addIncomePlan(AddIncomePlanDTO addIncomePlanDTO,Long userId) throws ParseException;
    public boolean deleteIncomePlan(Long incomePlanId);
    public List<IncomePlanVO> getIncomePlanList(Date startTime,Date endTime, Long userId) throws ParseException;
}
