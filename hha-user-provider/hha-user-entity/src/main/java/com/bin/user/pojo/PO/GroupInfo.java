package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (GroupInfo)表实体类
 *
 * @author tageshi
 * @since 2023-04-03 00:47:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo implements Serializable {
    
    private Long groupId;
    
    private String groupName;
    //群主id
    private Long groupLeader;
    private String groupImg;
    
    private Date createdTime;
    
    private Integer isDeleted;

}

