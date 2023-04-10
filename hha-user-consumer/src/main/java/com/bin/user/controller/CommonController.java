package com.bin.user.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.bin.common.core.api.AppHttpCodeEnum;
import com.bin.common.core.api.ResponseResult;
import com.bin.common.core.constants.SystemConstants;
import com.bin.common.core.utils.QiNiuUtils;
import com.bin.common.web.base.BaseController;
import com.bin.user.api.CodeService;
import com.bin.user.api.UserInfoService;
import com.bin.user.pojo.DTO.LoginByCodeDTO;
import com.bin.user.pojo.DTO.LoginByPasswordDTO;
import com.bin.user.pojo.DTO.RegisterDTO;
import com.bin.user.pojo.VO.UserInfoVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * @author tageshi
 * @date 2023/3/15 16:19
 */
@RestController
@Validated
@RequestMapping("/common")
public class CommonController extends BaseController {
    @Reference
    private UserInfoService userInfoService;
    @Reference
    private CodeService codeService;

    /**
     * @description:发送验证码
     * @author: tageshi
     * @date: 2023/3/15 19:37
     **/
    @GetMapping("/sendCode")
    public ResponseResult sendCode(String phoneNumber){
        if(codeService.sendCode(phoneNumber)){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.CODE_ERROR);
    }

    /**
     * @description:校验验证码
     * @author: tageshi
     * @date: 2023/3/15 20:24
     **/
    @PostMapping("/validateCode")
    public ResponseResult validateCode(String phoneNumber,String code){
        if(codeService.validateCode(phoneNumber,code)){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    /**
     * @description:用户注册
     * @author: tageshi
     * @date: 2023/3/15 20:24
     **/
    @PostMapping("/register")
    public ResponseResult register(@Validated @RequestBody RegisterDTO registerDTO){
        if(!codeService.validateCode(registerDTO.getPhoneNumber(),registerDTO.getCode())){
            ResponseResult.errorResult(AppHttpCodeEnum.CODE_ERROR);
        }
        if(userInfoService.register(registerDTO)){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    /**
     * @description:账号密码登录
     * @author: tageshi
     * @date: 2023/3/15 19:01
     **/
    @PostMapping("/loginByPassword")
    public ResponseResult loginByPassword(@Validated @RequestBody LoginByPasswordDTO loginByPasswordDTO){
        UserInfoVO userInfoVO = userInfoService.loginByPassword(loginByPasswordDTO);
        // sa-token登录（通过用户id）
        StpUtil.login(userInfoVO.getUserInfo().getUserId());
        // 获取token
        String token = StpUtil.getTokenValue();
        // 获取session
        SaSession session = StpUtil.getSession();
        // session中放入用户信息
        session.set(SystemConstants.SESSION_USER_KEY, userInfoVO);
        return ResponseResult.okResult(token);
    }

    @PostMapping("/loginByCode")
    public ResponseResult loginByCode(@Validated @RequestBody LoginByCodeDTO loginByCodeDTO){
        UserInfoVO userInfoVO = userInfoService.loginByCode(loginByCodeDTO);
        // sa-token登录（通过用户id）
        StpUtil.login(userInfoVO.getUserInfo().getUserId());
        // 获取token
        String token = StpUtil.getTokenValue();
        // 获取session
        SaSession session = StpUtil.getSession();
        // session中放入用户信息
        session.set(SystemConstants.SESSION_USER_KEY, userInfoVO);
        return ResponseResult.okResult(token);
    }

    /**
     * @description:退出登录
     * @author: tageshi
     * @date: 2023/3/15 19:01
     **/
    @DeleteMapping("/logout")
    public ResponseResult logout() {
        StpUtil.logout();
        return ResponseResult.okResult();
    }

    @GetMapping("/getQiNiuToken")
    public ResponseResult getQiNiuToken(){
        return ResponseResult.okResult(QiNiuUtils.getUploadToken());
    }
}
