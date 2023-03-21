package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.PO.OutcomeInfo;

/**
 * @author tageshi
 * @date 2023/3/16 17:38
 */
public interface OutcomeService extends IService<OutcomeInfo> {
    public boolean addOutcomeRecord(AddOutcomeRecordDTO addOutcomeRecordDTO,Long userId);
}
