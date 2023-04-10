package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.user.pojo.DTO.LoginByCodeDTO;
import com.bin.user.pojo.DTO.LoginByPasswordDTO;
import com.bin.user.pojo.DTO.RegisterDTO;
import com.bin.user.pojo.PO.UserInfo;
import com.bin.user.pojo.VO.GetFriendInfoVO;
import com.bin.user.pojo.VO.UserInfoVO;

/**
 * @author tageshi
 * @date 2023/3/15 16:47
 */
public interface UserInfoService extends IService<UserInfo> {
    UserInfoVO loginByPassword(LoginByPasswordDTO loginByPasswordDTO);
    UserInfoVO loginByCode(LoginByCodeDTO loginByCodeDTO);
    boolean register(RegisterDTO registerDTO);
    String getAgeGroupByUserId(Long userId);
    GetFriendInfoVO getUserInfoById(Long userId);
}
