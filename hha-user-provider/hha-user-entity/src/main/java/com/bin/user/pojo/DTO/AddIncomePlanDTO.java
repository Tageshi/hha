package com.bin.user.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/3/31 21:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddIncomePlanDTO implements Serializable {
    private String incomePlanName;
    private float cost;
    private Long typeId;
    private Date date;
}
