package com.bin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.user.pojo.PO.UserInfo;
import com.bin.user.pojo.VO.GetFriendInfoVO;
import com.bin.user.pojo.VO.UserInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author tageshi
 * @date 2023/3/15 16:54
 */
public interface UserInfoDao extends BaseMapper<UserInfo> {
    UserInfo getUserInfoByPhoneNumber(@Param("phoneNumber")String phoneNumber);
    String getPhoneNumberByUserId(@Param("userId")Long userId);
    String getUserNameByUserId(@Param("userId")Long userId);
    Integer register(@Param("phoneNumber")String phoneNumber,
                     @Param("username")String username,
                     @Param("password")String password,
                     @Param("sex")String sex,
                     @Param("headImg")String headImg,
                     @Param("email")String email,
                     @Param("ageGroup")String ageGroup);
    Integer updateScore(@Param("phoneNumber")String phoneNumber);
    String getAgeGroupByUserId(@Param("userId")Long userId);
    GetFriendInfoVO getFriendInfo(@Param("userId")Long userId);
}