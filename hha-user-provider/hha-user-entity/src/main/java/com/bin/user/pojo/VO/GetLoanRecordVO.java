package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/1 1:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLoanRecordVO implements Serializable {
    private Long loanId;
    private String loanName;
    private Integer pastMonth;
    private Integer loanDuration;
}
