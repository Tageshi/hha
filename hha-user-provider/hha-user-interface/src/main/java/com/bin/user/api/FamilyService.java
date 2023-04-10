package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.DTO.CreateFamilyDTO;
import com.bin.user.pojo.PO.FamilyInfo;
import com.bin.user.pojo.VO.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/1 1:25
 */
public interface FamilyService extends IService<FamilyInfo> {
    public boolean createFamily(CreateFamilyDTO createFamilyDTO,Long userId);
    public GetFamilyInfoVO getFamilyMemberList(Long userId);
    public boolean updateFamilyInfo(Long familyId,CreateFamilyDTO createFamilyDTO,Long userId);
    public boolean createJointExpenses(AddOutcomeRecordDTO addOutcomeRecordDTO,Long userId);
    public List<GetJointExpenseVO> getJointExpenseList(Date startTime, Date endTime, Long typeId, Long userId);
    public boolean deleteJointExpense(Long jointOutcomeId);
    public List<GetAllFamilyBillsVO> getAllFamilyBills(Date startTime, Date endTime, Long typeId, Long memberUserId,Long userId);
    public GetRatioVO getRatio(Date startTime,Date endTime,Long userId);
    public List<GetTypeRatioVO> getTypeRatio(Long userId);

}
