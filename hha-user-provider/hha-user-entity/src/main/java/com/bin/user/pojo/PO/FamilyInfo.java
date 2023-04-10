package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (FamilyInfo)表实体类
 *
 * @author tageshi
 * @since 2023-04-01 01:24:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyInfo implements Serializable {
    
    private Long familyId;
    
    private String familyName;
    
    private Long userId;
    
    private Date createdTime;
    
    private Integer isDeleted;

}

