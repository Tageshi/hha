package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.common.core.api.AppHttpCodeEnum;
import com.bin.common.core.exception.BusinessException;
import com.bin.user.api.UserInfoService;
import com.bin.user.dao.UserInfoDao;
import com.bin.user.pojo.DTO.LoginByCodeDTO;
import com.bin.user.pojo.DTO.LoginByPasswordDTO;
import com.bin.user.pojo.DTO.RegisterDTO;
import com.bin.user.pojo.PO.UserInfo;
import com.bin.user.pojo.VO.GetFriendInfoVO;
import com.bin.user.pojo.VO.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tageshi
 * @date 2023/3/15 16:53
 */
@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * @description: 账号密码登录
     * @author: tageshi
     * @date: 2023/3/16 17:18
     **/
    @Override
    public UserInfoVO loginByPassword(LoginByPasswordDTO loginByPasswordDTO) {
        String phoneNumber=loginByPasswordDTO.getPhoneNumber();
        String password= loginByPasswordDTO.getPassword();
        UserInfo userInfo=userInfoDao.getUserInfoByPhoneNumber(phoneNumber);
        if (userInfo == null) {
            throw BusinessException.newInstance(AppHttpCodeEnum.LOGIN_ERROR);
        }
        if (!userInfo.getPassword().equals(password)) {
            throw BusinessException.newInstance(AppHttpCodeEnum.LOGIN_ERROR);
        }
        //登录领取积分
        userInfoDao.updateScore(loginByPasswordDTO.getPhoneNumber());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserInfo(userInfo);
        return userInfoVO;
    }

    /**
     * @description:验证码登录
     * @author: tageshi
     * @date: 2023/3/16 17:18
     **/
    @Override
    public UserInfoVO loginByCode(LoginByCodeDTO loginByCodeDTO) {
        UserInfo userInfo=userInfoDao.getUserInfoByPhoneNumber(loginByCodeDTO.getPhoneNumber());
        if (userInfo == null) {
            throw BusinessException.newInstance(AppHttpCodeEnum.LOGIN_ERROR);
        }
        if (!loginByCodeDTO.getCode().equals("123456")) { //校验验证码
            throw BusinessException.newInstance(AppHttpCodeEnum.LOGIN_ERROR);
        }
        //登录领取积分
        userInfoDao.updateScore(loginByCodeDTO.getPhoneNumber());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserInfo(userInfo);
        return userInfoVO;
    }

    /**
     * @description:用户注册
     * @author: tageshi
     * @date: 2023/3/16 17:19
     **/
    @Override
    public boolean register(RegisterDTO registerDTO) {
        return userInfoDao.register(registerDTO.getPhoneNumber(),registerDTO.getUsername(),registerDTO.getPassword(),
                registerDTO.getSex(),registerDTO.getHeadImg(),registerDTO.getEmail(),registerDTO.getAgeGroup())>0;
    }

    @Override
    public String getAgeGroupByUserId(Long userId) {
        return userInfoDao.getAgeGroupByUserId(userId);
    }

    @Override
    public GetFriendInfoVO getUserInfoById(Long userId) {
        return userInfoDao.getFriendInfo(userId);
    }
}
