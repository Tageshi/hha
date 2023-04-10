package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/4/2 15:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestVO implements Serializable {
    private Long userId;
    private String userName;
    private String headImg;
    private Date createdTime;
}
