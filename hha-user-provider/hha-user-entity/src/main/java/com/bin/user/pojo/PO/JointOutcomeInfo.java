package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (JointOutcomeInfo)表实体类
 *
 * @author tageshi
 * @since 2023-04-01 16:57:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JointOutcomeInfo implements Serializable {
    
    private Long jointOutcomeId;
    
    private String jointOutcomeName;
    
    private Float jointOutcome;
    
    private Date date;
    
    private Long typeId;
    
    private Long familyId;
    
    private Date createdTime;
    
    private Integer isDeleted;

}

