package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.user.pojo.DTO.*;
import com.bin.user.pojo.PO.OutcomeInfo;
import com.bin.user.pojo.PO.SceneInfo;
import com.bin.user.pojo.VO.OutcomeRecordVO;
import com.bin.user.pojo.VO.RoutineDetailVO;
import com.bin.user.pojo.VO.RoutineInfoVO;
import com.bin.user.pojo.VO.TypeInfoVO;

import java.util.Date;
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
    public List<RoutineInfoVO> getConsumeRoutineList(Long userId);
    public RoutineDetailVO getConsumeRoutineDetail(Long routineId);
    public boolean updateConsumeRoutine(AddConsumeRoutineDTO addConsumeRoutineDTO,Long routineId,Long userId);
    public boolean deleteConsumeRoutine(Long routineId);
    public boolean addRoutinedOutcome(AddRoutinedOutcomeDTO addRoutinedOutcomeDTO); //定时任务、还没写完
    public List<SceneInfo> getConsumeSceneList();
    public List<String> getConsumeSceneDetail(Long sceneId);
    public List<OutcomeRecordVO> getRecentOutcomes(Long typeId,  Long userId);
    public List<OutcomeRecordVO> getAllOutcomes(Date startTime, Date endTime, Long typeId, Long userId);

}
