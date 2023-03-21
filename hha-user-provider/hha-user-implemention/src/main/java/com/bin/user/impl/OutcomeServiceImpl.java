package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.user.api.OutcomeService;
import com.bin.user.dao.OutcomeInfoDao;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.PO.OutcomeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author tageshi
 * @date 2023/3/17 13:37
 */
@Service
@Transactional
public class OutcomeServiceImpl extends ServiceImpl<OutcomeInfoDao, OutcomeInfo> implements OutcomeService {
    @Autowired
    private OutcomeInfoDao outcomeInfoDao;
    @Override
    public boolean addOutcomeRecord(AddOutcomeRecordDTO addOutcomeRecordDTO,Long userId) {
        return outcomeInfoDao.insertOutcomeRecord(addOutcomeRecordDTO.getTypeId(),addOutcomeRecordDTO.getOutcomeName(),addOutcomeRecordDTO.getOutcome(),userId)>0;
    }
}
