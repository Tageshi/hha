package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.user.api.OutcomeService;
import com.bin.user.dao.OutcomeInfoDao;
import com.bin.user.pojo.DTO.*;
import com.bin.user.pojo.PO.*;
import com.bin.user.pojo.VO.OutcomeRecordVO;
import com.bin.user.pojo.VO.RoutineDetailVO;
import com.bin.user.pojo.VO.RoutineInfoVO;
import com.bin.user.pojo.VO.TypeInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        //初始化消费类型名称和icon
        String name = "";
        String icon = "";
        //封装routine对象
        RoutineInfo routine = new RoutineInfo();
        routine.setRoutineName(addConsumeRoutineDTO.getRoutineName());
        routine.setRoutineIcon(addConsumeRoutineDTO.getRoutineIcon());
        //插入路线
        if(outcomeInfoDao.addConsumeRoutine(routine,userId)>0){
            //插入路线详情
            for (RoutineDetail rd:routineDetails) {
                //获取消费类型名称和icon
                icon = outcomeInfoDao.getTypeIcon(rd.getDetailTypeId());
                name = outcomeInfoDao.getTypeName(rd.getDetailTypeId());
                outcomeInfoDao.addRoutineDetail(rd.getDetailName(),rd.getDetailTypeId(),name,icon,rd.getDetailCost(),routine.getRoutineId());
            }
            return true;
        }
        return false;
    }

    /**
     * @description: 查看固定消费路线列表
     * @author: tageshi 
     * @date: 2023/3/28 0:10
     **/
    @Override
    public List<RoutineInfoVO> getConsumeRoutineList(Long userId) {
        return outcomeInfoDao.getRoutineList(userId);
    }

    /**
     * @description: 查看固定消费路线详情
     * @author: tageshi
     * @date: 2023/3/28 0:44
     **/
    @Override
    public RoutineDetailVO getConsumeRoutineDetail(Long routineId) {
        //封装routine对象
        RoutineDetailVO routineDetailVO = new RoutineDetailVO();
        RoutineInfoVO routine = outcomeInfoDao.getRoutineInfo(routineId);
        routineDetailVO.setRoutineName(routine.getRoutineName());
        routineDetailVO.setRoutineIcon(routine.getRoutineIcon());
        routineDetailVO.setRoutineDetail(outcomeInfoDao.getRoutineItem(routineId));
        return routineDetailVO;
    }

    /**
     * @description: 修改固定消费路线
     * @author: tageshi
     * @date: 2023/3/28 10:24
     **/
    @Override
    public boolean updateConsumeRoutine(AddConsumeRoutineDTO addConsumeRoutineDTO, Long routineId,Long userId) {
        //获取detail数组
        List<RoutineDetail> routineDetails = addConsumeRoutineDTO.getRoutineDetail();
        //初始化消费类型名称和icon
        String name = "";
        String icon = "";
        //封装routine对象
        RoutineInfo routine = new RoutineInfo();
        routine.setRoutineName(addConsumeRoutineDTO.getRoutineName());
        routine.setRoutineIcon(addConsumeRoutineDTO.getRoutineIcon());
        //先执行routine_info的修改
        if(outcomeInfoDao.updateConsumeRoutine(routineId,
                addConsumeRoutineDTO.getRoutineName(),addConsumeRoutineDTO.getRoutineIcon())<=0){
            return false;
        }
        //再执行routine_detail的删除
        if (outcomeInfoDao.deleteRoutineDetail(routineId)<=0){
            return false;
        }
        //后执行routine_detail的更新
        //插入路线详情
        for (RoutineDetail rd:routineDetails) {
            //获取消费类型名称和icon
            icon = outcomeInfoDao.getTypeIcon(rd.getDetailTypeId());
            name = outcomeInfoDao.getTypeName(rd.getDetailTypeId());
            if (outcomeInfoDao.addRoutineDetail(rd.getDetailName(),
                    rd.getDetailTypeId(),name,icon,rd.getDetailCost(),routineId)<=0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean deleteConsumeRoutine(Long routineId) {
        return outcomeInfoDao.deleteConsumeRoutine(routineId)>0 && outcomeInfoDao.deleteRoutineDetail(routineId)>0;
    }

    /**
     * @description: 定时任务添加固定路线支出
     * @author: tageshi
     * @date: 2023/3/28 17:37
     **/
    @Override
    public boolean addRoutinedOutcome(AddRoutinedOutcomeDTO addRoutinedOutcomeDTO) {
        //定时任务，没写
        return true;
    }

    /**
     * @description: 获取固定消费场景列表
     * @author: tageshi
     * @date: 2023/3/30 21:27
     **/
    @Override
    public List<SceneInfo> getConsumeSceneList() {
        return outcomeInfoDao.getConsumeSceneList();
    }

    @Override
    public List<String> getConsumeSceneDetail(Long sceneId) {
        return outcomeInfoDao.getConsumeSceneDetail(sceneId);
    }

    /**
     * @description: 查询近三月全部支出
     * @author: tageshi
     * @date: 2023/3/31 0:14
     **/
    @Override
    public List<OutcomeRecordVO> getRecentOutcomes(Long typeId, Long userId) {
        //获取前三个月的起始时间
        Date dateNow = new Date();   //当前时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dateNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -3);  //设置为前3月
        Date dateBefore = calendar.getTime();   //得到前3月的时间
        return outcomeInfoDao.getRecentOutcomes(typeId,userId,dateBefore);
    }

    /**
     * @description: 查询全部支出
     * @author: tageshi
     * @date: 2023/3/31 0:15
     **/
    @Override
    public List<OutcomeRecordVO> getAllOutcomes(Date startTime, Date endTime, Long typeId, Long userId) {
        return outcomeInfoDao.getAllOutcomes(startTime,endTime,typeId,userId);
    }


}
