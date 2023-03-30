package com.bin.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bin.common.core.api.ResponseResult;
import com.bin.common.web.base.BaseController;
import com.bin.quartz.QuartzProvider;
import com.bin.user.api.OutcomeService;
import com.bin.user.pojo.DTO.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bin.common.core.api.AppHttpCodeEnum.SUCCESS;
import static com.bin.common.core.api.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * @author tageshi
 * @date 2023/3/16 17:19
 */
@RestController
@RequestMapping("/youngster")
public class YoungsterController extends BaseController {

    @Reference
    private OutcomeService outcomeService;
    static Integer i=1;

    /**
     * @description:添加支出记录（记账）
     * @author: tageshi
     * @date: 2023/3/16 17:21
     **/
    @PostMapping("/addOutcomeRecord")
    public ResponseResult addOutcomeRecord(@Validated @RequestBody AddOutcomeRecordDTO addOutcomeRecordDTO){
        if(outcomeService.addOutcomeRecord(addOutcomeRecordDTO, getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查询消费类型列表
     * @author: tageshi
     * @date: 2023/3/26 21:25
     **/
    @GetMapping("/getTypeList")
    public ResponseResult getTypeList(){
        return ResponseResult.okResult(outcomeService.getTypeList(getUserId()));
    }
    /**
     * @description: 添加自定义消费类型
     * @author: tageshi
     * @date: 2023/3/26 20:33
     **/
    @PostMapping("/addConsumeType")
    public ResponseResult addConsumeType(@Validated @RequestBody AddConsumeTypeDTO addConsumeTypeDTO){
        if(outcomeService.addConsumeType(addConsumeTypeDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }
    /**
     * @description: 删除自定义消费类型
     * @author: tageshi
     * @date: 2023/3/26 21:26
     **/
    @PutMapping("/deleteConsumeType")
    public ResponseResult deleteConsumeType(@Validated Long typeId){
        if(outcomeService.deleteConsumeType(typeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 修改支出记录
     * @author: tageshi
     * @date: 2023/3/27 20:41
     **/
    @PutMapping("/updateOutcomeRecord")
    public ResponseResult updateOutcomeRecord(@Validated @RequestBody AddOutcomeRecordDTO addOutcomeRecordDTO,@Validated Long outcomeId){
        if(outcomeService.updateOutcomeRecord(addOutcomeRecordDTO,outcomeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 删除支出记录
     * @author: tageshi
     * @date: 2023/3/27 20:41
     **/
    @PutMapping("/deleteOutcomeRecord")
    public ResponseResult deleteOutcomeRecord(@Validated Long outcomeId){
        if(outcomeService.deleteOutcomeRecord(outcomeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description:添加固定消费路线
     * @author: tageshi
     * @date: 2023/3/27 21:12
     **/
    @PostMapping("/addConsumeRoutine")
    public ResponseResult addConsumeRoutine(@Validated @RequestBody AddConsumeRoutineDTO addConsumeRoutineDTO){
        //开启定时任务
        QuartzProvider quartzProvider = new QuartzProvider();
        switch (addConsumeRoutineDTO.getPeriod()){
            case "每天":
                quartzProvider.setCron("0 0 23 * * ? ");
                break;
            case "每周":
                quartzProvider.setCron("0 59 23 ? * SUN ");
                break;
            case "每月":
                quartzProvider.setCron("0 59 23 L * ? ");
                break;
            default:break;
        }
        if(quartzProvider.addQuartz() &&
                outcomeService.addConsumeRoutine(addConsumeRoutineDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查看固定消费路线列表
     * @author: tageshi 
     * @date: 2023/3/28 0:07
     **/
    @GetMapping("/getConsumeRoutineList")
    public ResponseResult getConsumeRoutineList(){
        return ResponseResult.okResult(outcomeService.getConsumeRoutineList(getUserId()));
    }

    /**
     * @description: 查看固定消费路线详情
     * @author: tageshi
     * @date: 2023/3/28 1:02
     **/
    @GetMapping("/getConsumeRoutineDetail")
    public ResponseResult getConsumeRoutineDetail(@Validated Long routineId){
        return ResponseResult.okResult(outcomeService.getConsumeRoutineDetail(routineId));
    }

    /**
     * @description: 修改固定消费路线
     * @author: tageshi
     * @date: 2023/3/28 10:07
     **/
    @PutMapping("/updateConsumeRoutine")
    public ResponseResult updateConsumeRoutine(@Validated @RequestBody AddConsumeRoutineDTO addConsumeRoutineDTO,@Validated Long routineId){
        if(outcomeService.updateConsumeRoutine(addConsumeRoutineDTO,routineId,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 删除固定消费路线
     * @author: tageshi
     * @date: 2023/3/28 10:51
     **/
    @PutMapping("/deleteConsumeRoutine")
    public ResponseResult deleteConsumeRoutine(@Validated Long routineId){
        if(outcomeService.deleteConsumeRoutine(routineId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }
    /**
     * @description: 按照固定消费路线记账
     * @author: tageshi
     * @date: 2023/3/28 16:58
     **/
    @PostMapping("/addRoutinedOutcome")
    public ResponseResult addRoutinedOutcome(@Validated @RequestBody AddRoutinedOutcomeDTO addRoutinedOutcomeDTO){
        return ResponseResult.okResult(outcomeService.addRoutinedOutcome(addRoutinedOutcomeDTO));
    }

    /**
     * @description: 获取固定消费场景列表（系统提供）
     * @author: tageshi
     * @date: 2023/3/30 21:25
     **/
    @GetMapping("/getConsumeSceneList")
    public ResponseResult getConsumeSceneList(){
        return ResponseResult.okResult(outcomeService.getConsumeSceneList());
    }

    /**
     * @description: 获取固定消费场景详情
     * @author: tageshi
     * @date: 2023/3/30 21:59
     **/
    @GetMapping("/getConsumeSceneDetail")
    public ResponseResult getConsumeSceneDetail(@Validated Long sceneId){
        return ResponseResult.okResult(outcomeService.getConsumeSceneDetail(sceneId));
    }

    @PostMapping("/addScenedOutcome")
    public ResponseResult addScenedOutcome(@Validated @RequestBody AddOutcomeRecordDTO addOutcomeRecordDTO){
        if(outcomeService.addOutcomeRecord(addOutcomeRecordDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查询近三月消费支出
     * @author: tageshi
     * @date: 2023/3/30 22:06
     **/
    @GetMapping("/getRecentOutcomes")
    public ResponseResult getRecentOutcomes(@Validated Long typeId){
        return ResponseResult.okResult(outcomeService.getRecentOutcomes(typeId,getUserId()));
    }

    /**
     * @description: 查询全部支出
     * @author: tageshi
     * @date: 2023/3/31 0:13
     **/
    @GetMapping("/getAllOutcomes")
    public ResponseResult getAllOutcomes(@Validated String startTime,
                                            @Validated String endTime,
                                            @Validated Long typeId) throws ParseException {
        Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
        return ResponseResult.okResult(outcomeService.getAllOutcomes(start,end,typeId,getUserId()));
    }
}