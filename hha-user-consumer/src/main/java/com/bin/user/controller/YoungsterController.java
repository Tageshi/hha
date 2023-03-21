package com.bin.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bin.common.core.api.AppHttpCodeEnum;
import com.bin.common.core.api.ResponseResult;
import com.bin.common.web.base.BaseController;
import com.bin.user.api.OutcomeService;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if(outcomeService.addOutcomeRecord(addOutcomeRecordDTO,getUserId())){
            System.out.println(getUserId());
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}