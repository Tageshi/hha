package com.bin.user.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/15 19:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO implements Serializable {
    private String phoneNumber;
    private String code;//验证码
    private String username;
    private String password;
    private String sex;
    private String headImg;
    private String email;
    private String ageGroup;
}
