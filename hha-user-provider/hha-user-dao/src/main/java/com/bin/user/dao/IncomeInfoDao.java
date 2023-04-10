package com.bin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.user.pojo.PO.IncomeInfo;
import com.bin.user.pojo.VO.IncomePlanVO;
import com.bin.user.pojo.VO.IncomeRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/31 13:47
 */
public interface IncomeInfoDao extends BaseMapper<IncomeInfo> {
    public Integer insertIncomeRecord(@Param("typeId")Long typeId,
                                      @Param("incomeName")String incomeName,
                                      @Param("income")Float income,
                                      @Param("date")Date date,
                                      @Param("userId")Long userId);
    public Integer updateIncomeRecord(@Param("typeId")Long typeId,
                                      @Param("incomeName")String incomeName,
                                      @Param("income")Float income,
                                      @Param("date")Date date,
                                      @Param("incomeId")Long incomeId);
    public Integer deleteIncomeRecord(@Param("incomeId")Long incomeId);
    public List<IncomeRecordVO> getIncomeList(@Param("startTime")Date startTime,
                                              @Param("endTime")Date endTime,
                                              @Param("typeId")Long typeId,
                                              @Param("userId")Long userId);

    public Integer addIncomePlan(@Param("incomePlanName")String incomePlanName,
                                 @Param("cost")float cost,
                                 @Param("typeId")Long typeId,
                                 @Param("date")Date date,
                                 @Param("userId")Long userId);
    public Integer deleteIncomePlan(@Param("incomePlanId")Long incomePlanId);
    public List<IncomePlanVO> getIncomePlanList(@Param("startTime") Date startTime,
                                                @Param("endTime") Date endTime,
                                                @Param("userId") Long userId);

}
