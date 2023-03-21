package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (OutcomeInfo)表实体类
 *
 * @author tageshi
 * @since 2023-03-16 17:35:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeInfo implements Serializable {
    
    private Long outcomeId;
    
    private String outcomeName;
    
    private Long userId;
    
    private Float outcome;
    
    private Long typeId;
    //需要另外转换成date类型
    private Date date;
    
    private Integer isDeleted;

}

