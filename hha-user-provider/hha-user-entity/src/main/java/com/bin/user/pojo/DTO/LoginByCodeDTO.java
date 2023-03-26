package com.bin.user.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/15 20:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginByCodeDTO implements Serializable {
    private String phoneNumber;
    private String code;
}