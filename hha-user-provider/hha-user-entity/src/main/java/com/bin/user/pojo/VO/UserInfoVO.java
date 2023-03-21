package com.bin.user.pojo.VO;

import com.bin.user.pojo.PO.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/15 16:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO implements Serializable {
    private UserInfo userInfo;
}

