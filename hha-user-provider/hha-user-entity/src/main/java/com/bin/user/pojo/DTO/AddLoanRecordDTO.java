package com.bin.user.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/3/31 22:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddLoanRecordDTO implements Serializable {
    private String loanName;
    private float loanSum;
    private float monthlyPayment;
    private Integer pastMonth;
    private Integer loanDuration;
}
