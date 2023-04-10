package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/4/4 10:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelOutcomeVO implements Serializable {
    /*Excel账单信息*/
    private String userName;
    private String outcomeName;
    private Float outcome;
    private String typeName;
    private Date date;
}
