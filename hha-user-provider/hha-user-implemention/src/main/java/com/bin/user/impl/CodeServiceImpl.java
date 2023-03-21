package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bin.common.core.utils.SMSUtils;
import com.bin.user.api.CodeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author tageshi
 * @date 2023/3/15 20:08
 */
@Service
@Transactional
public class CodeServiceImpl implements CodeService {
    @Override
    public boolean sendCode(String phoneNumber) {
        String code=String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
        //将验证码保存到缓存中
        /*return smsUtils.sendMessage(phoneNumber,code);*/
        return SMSUtils.sendMessage(phoneNumber,"123456");
    }

    @Override
    public boolean validateCode(String phoneNumber, String code) {
        //从缓存中获取验证码
        return code.equals("123456");
    }
}
