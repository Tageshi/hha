package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/2 17:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFriendInfoVO implements Serializable {
    private Long userId;
    private String phoneNumber;
    private String userName;
    private String sex;
    private String headImg;
    private String ageGroup;
}
