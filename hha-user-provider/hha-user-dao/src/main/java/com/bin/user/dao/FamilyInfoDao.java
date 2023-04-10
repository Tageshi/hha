package com.bin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.user.pojo.PO.FamilyInfo;
import com.bin.user.pojo.PO.JointOutcomeInfo;
import com.bin.user.pojo.PO.OutcomeInfo;
import com.bin.user.pojo.VO.GetFamilyMemberVO;
import com.bin.user.pojo.VO.GetJointExpenseVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/1 1:24
 */
public interface FamilyInfoDao extends BaseMapper<FamilyInfo> {
    @Options(useGeneratedKeys = true, keyProperty = "familyId", keyColumn = "family_id")
    @Insert(" insert into family_info(family_name,user_id) values (#{familyInfo.familyName},#{familyInfo.userId})")
    public Integer addFamily(@Param("familyInfo")FamilyInfo familyInfo);
    public Integer addFamilyMember(@Param("familyId")Long familyId,
                                   @Param("userId")Long userId);
    public List<GetFamilyMemberVO> getFamilyMemberList(@Param("familyId")Long familyId);
    public Long getFamilyIdByUserId(@Param("userId")Long userId);
    public FamilyInfo getFamilyInfoById(@Param("familyId")Long familyId);
    public Integer updateFamilyName(@Param("familyId")Long familyId,
                                           @Param("familyName")String familyName);
    public Integer deleteAllMembers(@Param("familyId")Long familyId);
    @Options(useGeneratedKeys = true, keyProperty = "outcomeId", keyColumn = "outcome_id")
    @Insert("insert into outcome_info(outcome_name,user_id,outcome,type_id,date) " +
            "values (#{outcomeInfo.outcomeName},#{outcomeInfo.userId},#{outcomeInfo.outcome}," +
            "#{outcomeInfo.typeId},#{outcomeInfo.date})")
    public Integer insertOutcomeRecord(@Param("outcomeInfo") OutcomeInfo outcomeInfo);
    @Options(useGeneratedKeys = true, keyProperty = "jointOutcomeId", keyColumn = "(joint_outcome_id")
    @Insert("insert into joint_outcome_info(joint_outcome_name,family_id,joint_outcome,type_id,date)\n" +
            "        values (#{jointOutcomeInfo.jointOutcomeName},#{jointOutcomeInfo.familyId}," +
            "#{jointOutcomeInfo.jointOutcome},#{jointOutcomeInfo.typeId},#{jointOutcomeInfo.date})")
    public Integer insertJointOutcomeRecord(@Param("jointOutcomeInfo") JointOutcomeInfo jointOutcomeInfo);
    public Integer insertJointOutcomeRelated(@Param("jointOutcomeId")Long jointOutcomeId,
                                             @Param("outcomeId")Long outcomeId);
    public List<GetJointExpenseVO>getJointExpenseList(@Param("startTime")Date startTime,@Param("endTime")Date endTime,
                                                      @Param("typeId")Long typeId,@Param("familyId")Long familyId);
    public List<Long>getOutcomeIdByJointOutcomeId(@Param("jointOutcomeId")Long jointOutcomeId);
    public Integer deleteJointRelated(@Param("outcomeId")Long outcomeId);
    public Integer deleteJointExpense(@Param("jointOutcomeId")Long jointOutcomeId);
    public Float getTypeTotalCost(@Param("familyId")Long familyId,
                                  @Param("typeId")Long typeId);
    public Float getDefinedTypeTotalCost(@Param("familyId")Long familyId);
    public List<Long>getLoanIdByFamilyId(@Param("familyId")Long familyId);


}
