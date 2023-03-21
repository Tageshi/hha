package com.bin.user.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.alibaba.dubbo.config.annotation.Reference;
import com.bin.user.api.UserInfoService;

import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/19 19:22
 */
public class StpInterfaceImpl implements StpInterface {
    @Reference
    UserInfoService userInfoService;
    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String s) {
        String userId = (String) loginId;
        List<String> roleNameList=null;
        roleNameList.add(userInfoService.getAgeGroupByUserId(Long.valueOf(userId)));
        return roleNameList;
    }
}
