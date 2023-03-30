package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/3/30 22:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeRecordVO implements Serializable {
    private Long outcomeId;
    private String outcomeName;
    private Float outcome;
    private Long typeId;
    private String typeName;
    private String typeIcon;
    private Date date;
}
