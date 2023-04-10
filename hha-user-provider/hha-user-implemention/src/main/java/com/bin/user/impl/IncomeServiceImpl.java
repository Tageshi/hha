package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.user.api.IncomeService;
import com.bin.user.dao.IncomeInfoDao;
import com.bin.user.pojo.DTO.AddIncomePlanDTO;
import com.bin.user.pojo.DTO.AddIncomeRecordDTO;
import com.bin.user.pojo.PO.IncomeInfo;
import com.bin.user.pojo.VO.IncomePlanVO;
import com.bin.user.pojo.VO.IncomeRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tageshi
 * @date 2023/3/31 13:46
 */
@Service
@Transactional
public class IncomeServiceImpl extends ServiceImpl<IncomeInfoDao, IncomeInfo> implements IncomeService {
    @Autowired
    private IncomeInfoDao incomeInfoDao;

    /**
     * @description: 添加固定收入（使用定时任务）
     * @author: tageshi
     * @date: 2023/3/31 16:45
     **/
    @Override
    public boolean addFixedIncome() {
        //使用定时任务
        return false;
    }

    @Override
    public boolean addIncomeRecord(AddIncomeRecordDTO addIncomeRecordDTO, Long userId) {
        //将金额保存至Redis并计算本月收入总额
        return incomeInfoDao.insertIncomeRecord(addIncomeRecordDTO.getTypeId(),addIncomeRecordDTO.getIncomeName(),
                addIncomeRecordDTO.getIncome(),addIncomeRecordDTO.getDate(),userId)>0;
    }

    @Override
    public boolean updateIncomeRecord(AddIncomeRecordDTO addIncomeRecordDTO, Long incomeId) {
        return incomeInfoDao.updateIncomeRecord(addIncomeRecordDTO.getTypeId(),addIncomeRecordDTO.getIncomeName(),
                addIncomeRecordDTO.getIncome(),addIncomeRecordDTO.getDate(),incomeId)>0;
    }

    @Override
    public boolean deleteIncomeRecord(Long incomeId) {
        return incomeInfoDao.deleteIncomeRecord(incomeId)>0;
    }

    /**
     * @description: 查询收入记录列表
     * @author: tageshi
     * @date: 2023/3/31 17:09
     **/
    @Override
    public List<IncomeRecordVO> getIncomeList(Date startTime, Date endTime, Long typeId,Long userId) {
        return incomeInfoDao.getIncomeList(startTime,endTime,typeId,userId);
    }

    @Override
    public boolean addIncomePlan(AddIncomePlanDTO addIncomePlanDTO, Long userId){
        return incomeInfoDao.addIncomePlan(addIncomePlanDTO.getIncomePlanName(), addIncomePlanDTO.getCost(), addIncomePlanDTO.getTypeId(), addIncomePlanDTO.getDate(), userId) > 0;
    }

    @Override
    public boolean deleteIncomePlan(Long incomePlanId) {
        return incomeInfoDao.deleteIncomePlan(incomePlanId)>0;
    }

    @Override
    public List<IncomePlanVO> getIncomePlanList(Date startTime,Date endTime, Long userId){
        return incomeInfoDao.getIncomePlanList(startTime,endTime,userId);
    }
}
