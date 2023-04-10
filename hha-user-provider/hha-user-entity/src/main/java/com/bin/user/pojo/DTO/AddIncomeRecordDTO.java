package com.bin.user.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/3/31 16:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddIncomeRecordDTO implements Serializable {
    private Long typeId;
    private String incomeName;
    private float income;
    private Date date;
}
