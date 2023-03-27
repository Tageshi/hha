package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (RoutineInfo)表实体类
 *
 * @author tageshi
 * @since 2023-03-27 21:15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineInfo implements Serializable {
    
    private Long routineId;
    
    private String routineName;
    private String routineIcon;
    
    private Long userId;
    
    private Date createdTime;
    
    private Integer isDeleted;

}

