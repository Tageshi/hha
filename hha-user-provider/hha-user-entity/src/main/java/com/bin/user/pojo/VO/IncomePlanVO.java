package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/3/31 21:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomePlanVO implements Serializable {
    private Long incomePlanId;
    private String incomePlanName;
    private float cost;
    private Long typeId;
    private String typeName;
    private String typeIcon;
}
