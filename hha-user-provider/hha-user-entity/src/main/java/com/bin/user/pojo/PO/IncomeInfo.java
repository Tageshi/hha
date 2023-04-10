package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (IncomeInfo)表实体类
 *
 * @author tageshi
 * @since 2023-03-31 13:45:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeInfo implements Serializable {
    
    private Long incomeId;
    
    private String incomeName;
    
    private Long userId;
    
    private Float income;
    
    private Long typeId;
    
    private Date date;
    
    private Integer isDeleted;
    
    private Date createdTime;
    
    private Date updateTime;

}

