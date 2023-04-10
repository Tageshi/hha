package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/3/31 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeRecordVO implements Serializable {
    private Long incomeId;
    private String incomeName;
    private Float income;
    private Long typeId;
    private String typeName;
    private String typeIcon;
    private Date date;
}
