package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.user.pojo.DTO.AddConsumeRoutineDTO;
import com.bin.user.pojo.DTO.AddConsumeTypeDTO;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.PO.OutcomeInfo;
import com.bin.user.pojo.VO.TypeInfoVO;

import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/16 17:38
 */
public interface OutcomeService extends IService<OutcomeInfo> {
    public boolean addOutcomeRecord(AddOutcomeRecordDTO addOutcomeRecordDTO,Long userId);
    public boolean addConsumeType(AddConsumeTypeDTO addConsumeTypeDTO,Long userId);
    public List<TypeInfoVO> getTypeList(Long userId);
    public boolean deleteConsumeType(Long typeId);
    public boolean updateOutcomeRecord(AddOutcomeRecordDTO addOutcomeRecordDTO,Long outcomeId);
    public boolean deleteOutcomeRecord(Long outcomeId);
    public boolean addConsumeRoutine(AddConsumeRoutineDTO addConsumeRoutineDTO,Long userId);
}
