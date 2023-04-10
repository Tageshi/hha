package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.user.api.FamilyService;
import com.bin.user.dao.FamilyInfoDao;
import com.bin.user.dao.OutcomeInfoDao;
import com.bin.user.dao.UserInfoDao;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.DTO.CreateFamilyDTO;
import com.bin.user.pojo.PO.FamilyInfo;
import com.bin.user.pojo.PO.FamilyMember;
import com.bin.user.pojo.PO.JointOutcomeInfo;
import com.bin.user.pojo.PO.OutcomeInfo;
import com.bin.user.pojo.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/1 1:26
 */
@Service
@Transactional
public class FamilyServiceImpl extends ServiceImpl<FamilyInfoDao, FamilyInfo> implements FamilyService {
    @Autowired
    private FamilyInfoDao familyInfoDao;
    @Autowired
    private OutcomeInfoDao outcomeInfoDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public boolean createFamily(CreateFamilyDTO createFamilyDTO,Long userId) {
        boolean flag = true;
        //添加家庭
        FamilyInfo familyInfo = new FamilyInfo();
        familyInfo.setFamilyName(createFamilyDTO.getFamilyName());
        familyInfo.setUserId(userId);
        if(familyInfoDao.addFamily(familyInfo)<=0){
            flag = false;
        }
        //添加家庭成员
        if(familyInfoDao.addFamilyMember(familyInfo.getFamilyId(),userId)<=0){
            flag = false;
        }
        List<FamilyMember> familyMembers = createFamilyDTO.getFamilyMembers();
        for (FamilyMember familyMember:familyMembers) {
            if(familyInfoDao.addFamilyMember(familyInfo.getFamilyId(),familyMember.getUserId())<=0){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public GetFamilyInfoVO getFamilyMemberList(Long userId) {
        //根据userId获取familyId
        Long familyId = familyInfoDao.getFamilyIdByUserId(userId);
        List<GetFamilyMemberVO> familyMembers = familyInfoDao.getFamilyMemberList(familyId);
        String familyName = familyInfoDao.getFamilyInfoById(familyId).getFamilyName();
        GetFamilyInfoVO familyInfo = new GetFamilyInfoVO();
        familyInfo.setMembers(familyMembers);
        familyInfo.setFamilyName(familyName);
        //根据familyId获取member列表
        return familyInfo;
    }

    @Override
    public boolean updateFamilyInfo(Long familyId, CreateFamilyDTO createFamilyDTO,Long userId) {
        boolean flag=true;
        //修改家庭名称
        familyInfoDao.updateFamilyName(familyId,createFamilyDTO.getFamilyName());
        //先删除所有家庭成员
        familyInfoDao.deleteAllMembers(familyId);
        //再增加所有家庭成员
        //添加家庭成员
        if(familyInfoDao.addFamilyMember(familyId,userId)<=0){
            flag = false;
        }
        List<FamilyMember> familyMembers = createFamilyDTO.getFamilyMembers();
        for (FamilyMember familyMember:familyMembers) {
            if(familyInfoDao.addFamilyMember(familyId,familyMember.getUserId())<=0){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean createJointExpenses(AddOutcomeRecordDTO addOutcomeRecordDTO, Long userId) {
        boolean flag = true;
        //获取userId对应的family_id
        Long familyId = familyInfoDao.getFamilyIdByUserId(userId);
        //找出对应的userId列表
        List<GetFamilyMemberVO> members = familyInfoDao.getFamilyMemberList(familyId);
        //创建对应的OutcomeInfo和JointOutcomeInfo
        OutcomeInfo outcomeInfo = new OutcomeInfo();
        JointOutcomeInfo jointOutcomeInfo = new JointOutcomeInfo();
        //向joint_outcome_info插入数据
        jointOutcomeInfo.setTypeId(addOutcomeRecordDTO.getTypeId());
        jointOutcomeInfo.setJointOutcomeName(addOutcomeRecordDTO.getOutcomeName());
        jointOutcomeInfo.setJointOutcome(addOutcomeRecordDTO.getOutcome());
        jointOutcomeInfo.setDate(addOutcomeRecordDTO.getDate());
        jointOutcomeInfo.setFamilyId(familyId);
        if(familyInfoDao.insertJointOutcomeRecord(jointOutcomeInfo)<0){
            flag = false;
        }
        //根据user_family向对应user的outcome_info插入支出记录
        for (GetFamilyMemberVO member:members) {
            outcomeInfo.setTypeId(addOutcomeRecordDTO.getTypeId());
            outcomeInfo.setOutcomeName(addOutcomeRecordDTO.getOutcomeName());
            outcomeInfo.setOutcome(addOutcomeRecordDTO.getOutcome()/members.size());
            outcomeInfo.setDate(addOutcomeRecordDTO.getDate());
            outcomeInfo.setUserId(member.getUserId());
            if(familyInfoDao.insertOutcomeRecord(outcomeInfo)<=0){
                flag = false;
            }
            //向joint_outcome_related插入数据
            if(familyInfoDao.insertJointOutcomeRelated(jointOutcomeInfo.getJointOutcomeId(),outcomeInfo.getOutcomeId())<=0) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public List<GetJointExpenseVO> getJointExpenseList(Date startTime, Date endTime, Long typeId, Long userId) {
        //获取userId对应的family_id
        Long familyId = familyInfoDao.getFamilyIdByUserId(userId);
        return familyInfoDao.getJointExpenseList(startTime,endTime,typeId,familyId);
    }

    @Override
    public boolean deleteJointExpense(Long jointOutcomeId) {
        boolean flag = true;
        //根据jointOutcomeId查找joint_outcome_related中的outcome_id数组
        List<Long> outcomeIds = familyInfoDao.getOutcomeIdByJointOutcomeId(jointOutcomeId);
        for (Long outcomeId:outcomeIds) {
            //删除related表数据
            if(familyInfoDao.deleteJointRelated(outcomeId)<=0){
                flag = false;
            }
            //删除OutcomeInfo表数据
           if(outcomeInfoDao.deleteOutcomeRecord(outcomeId)<=0){
               flag = false;
           }
        }
        //删除JointOutcomeInfo表数据
        if(familyInfoDao.deleteJointExpense(jointOutcomeId)<=0){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<GetAllFamilyBillsVO> getAllFamilyBills(Date startTime, Date endTime, Long typeId,
                                                       Long memberUserId, Long userId) {
        List<GetAllFamilyBillsVO> familyBills = new ArrayList<>();
        //若指定查询某家庭成员的支出记录
        if(memberUserId!=null){
            //查询该成员userName
            String memberUserName = userInfoDao.getUserNameByUserId(memberUserId);
            List<OutcomeRecordVO> outcomeRecords = outcomeInfoDao.getAllOutcomes(startTime,endTime,typeId,memberUserId);
            for (OutcomeRecordVO outcomeRecord:outcomeRecords) {
                GetAllFamilyBillsVO bill = new GetAllFamilyBillsVO(); // 每次循环创建一个新的对象
                bill.setOutcomeId(outcomeRecord.getOutcomeId());
                bill.setOutcomeName(outcomeRecord.getOutcomeName());
                bill.setTypeId(outcomeRecord.getTypeId());
                bill.setTypeName(outcomeRecord.getTypeName());
                bill.setTypeIcon(outcomeRecord.getTypeIcon());
                bill.setMemberUserId(memberUserId);
                bill.setMemberUserName(memberUserName);
                bill.setDate(outcomeRecord.getDate());
                familyBills.add(bill);
            }
        }
        else {
            //查询所有家庭成员的支出记录列表
            Long familyId = familyInfoDao.getFamilyIdByUserId(userId);
            List<GetFamilyMemberVO> members = familyInfoDao.getFamilyMemberList(familyId);
            List<OutcomeRecordVO> outcomeRecords;
            String memberUserName = "";
            for (GetFamilyMemberVO member:members) {
                memberUserName = userInfoDao.getUserNameByUserId(member.getUserId());
                outcomeRecords = outcomeInfoDao.getAllOutcomes(startTime,endTime,typeId,member.getUserId());
                for (OutcomeRecordVO outcomeRecord:outcomeRecords) {
                    GetAllFamilyBillsVO bill = new GetAllFamilyBillsVO(); // 每次循环创建一个新的对象
                    bill.setOutcomeId(outcomeRecord.getOutcomeId());
                    bill.setOutcomeName(outcomeRecord.getOutcomeName());
                    bill.setTypeId(outcomeRecord.getTypeId());
                    bill.setTypeName(outcomeRecord.getTypeName());
                    bill.setTypeIcon(outcomeRecord.getTypeIcon());
                    bill.setMemberUserId(member.getUserId());
                    bill.setMemberUserName(memberUserName);
                    bill.setDate(outcomeRecord.getDate());
                    familyBills.add(bill);
                }
            }
        }
        return familyBills;
    }

    /**
     * @description: 获取家庭成员支出金额占比
     * @author: tageshi
     * @date: 2023/4/1 23:35
     **/
    @Override
    public GetRatioVO getRatio(Date startTime,Date endTime,Long userId) {
        GetRatioVO getRatioVO = new GetRatioVO();
        List<MemberRatioVO> memberRatios = new ArrayList<>();
        //获取familyId
        Long familyId = familyInfoDao.getFamilyIdByUserId(userId);
        //获取familyMember的userId
        List<GetFamilyMemberVO> members = familyInfoDao.getFamilyMemberList(familyId);
        //计算ratio后分别set入MemberRatioVO对象中
        float familyTotalCost = 0;
        for (GetFamilyMemberVO member:members) {
            familyTotalCost += outcomeInfoDao.getMonthlyTotalCost(startTime,endTime,member.getUserId());
        }
        for (GetFamilyMemberVO member:members) {
            MemberRatioVO memberRatio = new MemberRatioVO();
            memberRatio.setUserId(member.getUserId());
            memberRatio.setUserName(member.getUserName());
            memberRatio.setRatio((outcomeInfoDao.getMonthlyTotalCost(startTime,
                    endTime,member.getUserId())/familyTotalCost)*100);//显示为原数值的100倍
            memberRatios.add(memberRatio);
        }
        getRatioVO.setMemberRatios(memberRatios);
        return getRatioVO;
    }

    @Override
    public List<GetTypeRatioVO> getTypeRatio(Long userId) {
        List<GetTypeRatioVO>getTypeRatios = new ArrayList<>();
        //获取familyId
        Long familyId = familyInfoDao.getFamilyIdByUserId(userId);
        //获取familyMember的userId
        List<GetFamilyMemberVO> members = familyInfoDao.getFamilyMemberList(familyId);
        //获取类型1-9
        List<TypeInfoVO>types = outcomeInfoDao.getTypeList();
        float sum = 0;
        for (TypeInfoVO type:types) {
            if(familyInfoDao.getTypeTotalCost(familyId,type.getTypeId())!=null) {
                sum += familyInfoDao.getTypeTotalCost(familyId, type.getTypeId());
            }
        }
        //加上自定义类型的金额总和
        if(familyInfoDao.getDefinedTypeTotalCost(familyId)!=null){
            sum += familyInfoDao.getDefinedTypeTotalCost(familyId);
        }
        for (TypeInfoVO type:types) {
            GetTypeRatioVO getTypeRatioVO = new GetTypeRatioVO();
            getTypeRatioVO.setTypeId(type.getTypeId());
            getTypeRatioVO.setTypeIcon(type.getTypeIcon());
            getTypeRatioVO.setTypeName(type.getTypeName());
            if(familyInfoDao.getTypeTotalCost(familyId,type.getTypeId())==null){
                getTypeRatioVO.setRatio(0);
            }
            else {
                getTypeRatioVO.setRatio((familyInfoDao.getTypeTotalCost(familyId,type.getTypeId())/sum)*100);
            }
            getTypeRatios.add(getTypeRatioVO);
        }
        //计算自定义金额占比
        GetTypeRatioVO getTypeRatioVO = new GetTypeRatioVO();
        getTypeRatioVO.setTypeId((long)10);
        getTypeRatioVO.setTypeIcon("ok");//typeIcon记得改
        getTypeRatioVO.setTypeName("其它");
        if(familyInfoDao.getDefinedTypeTotalCost(familyId)==null){
            getTypeRatioVO.setRatio(0);
        }
        else {
            getTypeRatioVO.setRatio((familyInfoDao.getDefinedTypeTotalCost(familyId)/sum)*100);
        }
        getTypeRatios.add(getTypeRatioVO);
        return getTypeRatios;
    }
}
