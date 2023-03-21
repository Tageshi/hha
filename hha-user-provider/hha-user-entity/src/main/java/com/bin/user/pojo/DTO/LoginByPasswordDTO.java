package com.bin.user.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/15 16:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginByPasswordDTO implements Serializable {
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;
    @NotBlank(message = "密码不能为空")
    private String password;
}
