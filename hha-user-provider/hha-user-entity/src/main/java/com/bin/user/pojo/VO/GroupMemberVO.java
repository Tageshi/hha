package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/3 2:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberVO implements Serializable {
    private Long userId;
    private String userName;
    private String headImg;
}
