package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.user.api.OutcomeService;
import com.bin.user.dao.OutcomeInfoDao;
import com.bin.user.pojo.DTO.AddConsumeRoutineDTO;
import com.bin.user.pojo.DTO.AddConsumeTypeDTO;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.PO.OutcomeInfo;
import com.bin.user.pojo.PO.RoutineDetail;
import com.bin.user.pojo.PO.RoutineInfo;
import com.bin.user.pojo.PO.TypeInfo;
import com.bin.user.pojo.VO.TypeInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author tageshi
 * @date 2023/3/17 13:37
 */
@Service
@Transactional
public class OutcomeServiceImpl extends ServiceImpl<OutcomeInfoDao, OutcomeInfo> implements OutcomeService {
    @Autowired
    private OutcomeInfoDao outcomeInfoDao;
    /**
     * @description: 添加支出（记账）
     * @author: tageshi
     * @date: 2023/3/26 20:25
     **/
    @Override
    public boolean addOutcomeRecord(AddOutcomeRecordDTO addOutcomeRecordDTO,Long userId) {
        return outcomeInfoDao.insertOutcomeRecord(addOutcomeRecordDTO.getTypeId(),
                addOutcomeRecordDTO.getOutcomeName(),
                addOutcomeRecordDTO.getOutcome(),
                addOutcomeRecordDTO.getDate(),userId)>0;
    }


    /**
     * @description: 查询消费类型列表
     * @author: tageshi
     * @date: 2023/3/26 21:14
     **/
    @Override
    public List<TypeInfoVO> getTypeList(Long userId) {
        //查询系统提供的消费类型
        List<TypeInfoVO>systemList = outcomeInfoDao.getTypeList();//目前只定义了id=2的类型为系统消费类型
        //查询自定义消费类型
        List<TypeInfoVO>definedList = outcomeInfoDao.getDefinedTypeList(userId);
        List<TypeInfoVO>list=new ArrayList<>();
        list.addAll(systemList);
        list.addAll(definedList);
        return list;
    }

    /**
     * @description: 添加自定义类型
     * @author: tageshi
     * @date: 2023/3/26 20:26
     **/
    @Override
    public boolean addConsumeType(AddConsumeTypeDTO addConsumeTypeDTO,Long userId) {
        //对type_info和user_type同时操作
        TypeInfo type = new TypeInfo();
        type.setTypeName(addConsumeTypeDTO.getTypeName());
        type.setTypeIcon(addConsumeTypeDTO.getTypeIcon());
        outcomeInfoDao.insertDefinedType(type);
        return outcomeInfoDao.insertUserType(userId, type.getTypeId())>0;
    }

    /**
     * @description: 删除自定义类型
     * @author: tageshi
     * @date: 2023/3/26 21:29
     **/
    @Override
    public boolean deleteConsumeType(Long typeId) {
        return outcomeInfoDao.deleteCascadeType(typeId)>0 && outcomeInfoDao.deleteOneType(typeId)>0;
    }

    /**
     * @description: 修改支出记录
     * @author: tageshi
     * @date: 2023/3/27 20:42
     **/
    @Override
    public boolean updateOutcomeRecord(AddOutcomeRecordDTO addOutcomeRecordDTO, Long outcomeId) {
        return outcomeInfoDao.updateOutcomeRecord(outcomeId,addOutcomeRecordDTO.getTypeId(),addOutcomeRecordDTO.getOutcomeName(),
                addOutcomeRecordDTO.getOutcome(),addOutcomeRecordDTO.getDate())>0;
    }

    /**
     * @description: 删除支出记录
     * @author: tageshi
     * @date: 2023/3/27 20:42
     **/
    @Override
    public boolean deleteOutcomeRecord(Long outcomeId) {
        return outcomeInfoDao.deleteOutcomeRecord(outcomeId)>0;
    }

    /**
     * @description: 添加固定路线
     * @author: tageshi
     * @date: 2023/3/27 21:19
     **/
    @Override
    public boolean addConsumeRoutine(AddConsumeRoutineDTO addConsumeRoutineDTO,Long userId) {
        //获取detail数组
        List<RoutineDetail> routineDetails = addConsumeRoutineDTO.getRoutineDetail();
        //封装routine对象
        RoutineInfo routine = new RoutineInfo();
        routine.setRoutineName(addConsumeRoutineDTO.getRoutineName());
        routine.setRoutineIcon(addConsumeRoutineDTO.getRoutineIcon());
        //插入路线
        if(outcomeInfoDao.addConsumeRoutine(routine,userId)>0){
            //插入路线详情
            for (RoutineDetail rd:routineDetails) {
                outcomeInfoDao.addRoutineDetail(rd.getDetailName(),rd.getDetailTypeId(),rd.getDetailCost(),routine.getRoutineId());
            }
            return true;
        }
        return false;
    }

}
