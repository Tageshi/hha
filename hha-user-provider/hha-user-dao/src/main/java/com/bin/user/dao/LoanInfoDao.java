package com.bin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.user.pojo.PO.LoanInfo;
import com.bin.user.pojo.VO.FamilyLoanDetailVO;
import com.bin.user.pojo.VO.GetLoanRecordVO;
import com.bin.user.pojo.VO.LoanDetailVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/31 22:12
 */
public interface LoanInfoDao extends BaseMapper<LoanInfo> {
    public Integer addLoanRecord(@Param("loanName")String loanName,
                                 @Param("loanSum")Float loanSum,
                                 @Param("monthlyPayment")Float monthlyPayment,
                                 @Param("pastMonth")Integer pastMonth,
                                 @Param("loanDuration")Integer loanDuration,
                                 @Param("loanDeadline")Date loanDeadline,
                                 @Param("userId")long userId);
    @Options(useGeneratedKeys = true, keyProperty = "loanId", keyColumn = "loan_id")
    @Insert("insert into loan_info(loan_name,user_id,loan_sum,monthly_payment,past_month,loan_duration,loan_deadline,is_family_loan)\n" +
            "        values (#{loanInfo.loanName},#{loanInfo.userId},#{loanInfo.loanSum},#{loanInfo.monthlyPayment},#{loanInfo.pastMonth},#{loanInfo.loanDuration},#{loanInfo.loanDeadline},#{loanInfo.isFamilyLoan})")
    public Integer addJointLoanRecord(@Param("loanInfo")LoanInfo loanInfo);
    public Integer addFamilyLoanRecord(@Param("loanId")long loanId,
                                       @Param("familyId")Long familyId);
    public Integer deleteLoanRecord(@Param("loanId")Long loanId);
    public List<GetLoanRecordVO> getAllLoanRecordList(@Param("userId")Long userId);
    public List<GetLoanRecordVO> getUnfinishedLoanRecordList(@Param("userId")Long userId);
    public List<GetLoanRecordVO> getFinishedLoanRecordList(@Param("userId")Long userId);
    public LoanDetailVO getLoanDetail(@Param("loanId")Long LoanId);
    public GetLoanRecordVO getLoanById(@Param("loanId")Long loanId);
    public GetLoanRecordVO getUnfinishedLoanById(@Param("loanId")Long loanId);
    public GetLoanRecordVO getFinishedLoanById(@Param("loanId")Long loanId);
    public FamilyLoanDetailVO getFamilyLoanDetail(@Param("loanId")Long LoanId);
    public Long getCreatorByLoanId(@Param("loanId")Long LoanId);
}
