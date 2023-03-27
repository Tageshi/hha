package com.bin.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bin.common.core.api.ResponseResult;
import com.bin.common.web.base.BaseController;
import com.bin.user.api.OutcomeService;
import com.bin.user.pojo.DTO.AddConsumeRoutineDTO;
import com.bin.user.pojo.DTO.AddConsumeTypeDTO;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        if(outcomeService.addConsumeRoutine(addConsumeRoutineDTO,getUserId())){
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

    @GetMapping("/getConsumeRoutineDetail")
    public ResponseResult getConsumeRoutineDetail(@Validated Long routineId){
        return ResponseResult.okResult(outcomeService.getConsumeRoutineDetail(routineId));
    }
}