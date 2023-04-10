package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (LoanInfo)表实体类
 *
 * @author tageshi
 * @since 2023-03-31 22:12:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanInfo implements Serializable {
    
    private Long loanId;
    
    private String loanName;
    
    private Long userId;
    
    private Float loanSum;
    
    private Float monthlyPayment;

    private Integer pastMonth;
    
    private Integer loanDuration;
    
    private Date loanDeadline;

    private Integer isFamilyLoan; //默认为0，表示非家庭贷款
    
    private Date createdTime;
    
    private Integer isDeleted;

}

