package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author tageshi
 * @date 2023/3/15 20:05
 */
public interface CodeService {
    boolean sendCode(String phoneNumber);
    boolean validateCode(String phoneNumber,String code);
}
