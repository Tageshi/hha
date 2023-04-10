package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/4/1 1:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetailVO implements Serializable {
    private String loanName;
    private Float loanSum;
    private Float monthlyPayment;
    private Integer pastMonth;
    private Integer loanDuration;
    private Date loanDeadline;
}
