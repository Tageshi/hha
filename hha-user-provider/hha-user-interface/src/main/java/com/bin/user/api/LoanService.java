package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.user.pojo.DTO.AddLoanRecordDTO;
import com.bin.user.pojo.PO.LoanInfo;
import com.bin.user.pojo.VO.FamilyLoanDetailVO;
import com.bin.user.pojo.VO.GetLoanRecordVO;
import com.bin.user.pojo.VO.LoanDetailVO;

import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/31 22:13
 */
public interface LoanService extends IService<LoanInfo> {
    public boolean addLoanRecord(AddLoanRecordDTO addLoanRecordDTO,Long userId);
    public boolean addJointLoanRecord(AddLoanRecordDTO addLoanRecordDTO,Long userId);
    public boolean deleteLoanRecord(Long loanId);
    public List<GetLoanRecordVO> getLoanRecordList(Integer isFinished,Long userId);
    public LoanDetailVO getLoanDetail(Long loanId);
    public List<GetLoanRecordVO> getFamilyLoanRecordList(Integer isFinished,Long userId);
    public FamilyLoanDetailVO getFamilyLoanDetail(Long loanId);
    public boolean deleteFamilyLoanRecord(Long loanId,Long userId);
}
