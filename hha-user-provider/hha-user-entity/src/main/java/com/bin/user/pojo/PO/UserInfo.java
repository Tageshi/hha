package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (UserInfo)表实体类
 *
 * @author tageshi
 * @since 2023-03-15 16:18:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    
    private Long userId;
    
    private String phoneNumber;
    
    private String username;
    
    private String password;
    
    private String sex;
    
    private String headImg;
    
    private String email;
    
    private String ageGroup;
    
    private Long score;
    
    private Date createdTime;
    
    private Integer isDeleted;

}

