package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/4/2 1:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyLoanDetailVO implements Serializable {
    private String loanName;
    private Float loanSum;
    private Float monthlyPayment;
    private Integer pastMonth;
    private Integer loanDuration;
    private Date loanDeadline;
    private Long userId;
    private String userName;
    private String headImg;
}