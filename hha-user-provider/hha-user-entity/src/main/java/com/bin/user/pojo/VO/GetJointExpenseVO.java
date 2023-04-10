package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/4/1 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetJointExpenseVO implements Serializable {
    private Long jointOutcomeId;
    private String jointOutcomeName;
    private float jointOutcome;
    private Long typeId;
    private String typeName;
    private String typeIcon;
    private Date date;
}
