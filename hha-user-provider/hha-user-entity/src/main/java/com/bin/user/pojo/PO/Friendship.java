package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (Friendship)表实体类
 *
 * @author tageshi
 * @since 2023-04-02 13:58:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friendship implements Serializable {
    
    private Long userId;
    
    private Long friendUserId;
    
    private Date createdTime;
    
    private Integer isDeleted;

}

