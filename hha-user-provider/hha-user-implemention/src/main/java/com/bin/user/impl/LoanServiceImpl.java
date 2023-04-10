package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.user.api.LoanService;
import com.bin.user.dao.FamilyInfoDao;
import com.bin.user.dao.LoanInfoDao;
import com.bin.user.pojo.DTO.AddLoanRecordDTO;
import com.bin.user.pojo.PO.LoanInfo;
import com.bin.user.pojo.VO.FamilyLoanDetailVO;
import com.bin.user.pojo.VO.GetLoanRecordVO;
import com.bin.user.pojo.VO.LoanDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/31 22:14
 */
@Service
@Transactional
public class LoanServiceImpl extends ServiceImpl<LoanInfoDao, LoanInfo> implements LoanService {
    @Autowired
    private LoanInfoDao loanInfoDao;
    @Autowired
    private FamilyInfoDao familyInfoDao;

    @Override
    public boolean addLoanRecord(AddLoanRecordDTO addLoanRecordDTO, Long userId) {
        /*爬取月供与用户核对*/
        /*计算deadline*/
        //获取当前月份
        Date dateNow = new Date();   //当前时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dateNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, addLoanRecordDTO.getLoanDuration()-addLoanRecordDTO.getPastMonth());  //获取截止日期
        Date deadline = calendar.getTime();   //得到deadline日期
        return loanInfoDao.addLoanRecord(addLoanRecordDTO.getLoanName(),addLoanRecordDTO.getLoanSum(),
                addLoanRecordDTO.getMonthlyPayment(),addLoanRecordDTO.getPastMonth(),addLoanRecordDTO.getLoanDuration(),deadline,userId)>0;
    }

    @Override
    public boolean addJointLoanRecord(AddLoanRecordDTO addLoanRecordDTO, Long userId) {
        /*爬取月供与用户核对*/
        /*计算deadline*/
        //获取当前月份
        Date dateNow = new Date();   //当前时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dateNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, addLoanRecordDTO.getLoanDuration()-addLoanRecordDTO.getPastMonth());  //获取截止日期
        Date deadline = calendar.getTime();   //得到deadline日期
        /*封装贷款对象*/
        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setLoanName(addLoanRecordDTO.getLoanName());
        loanInfo.setLoanSum(addLoanRecordDTO.getLoanSum());
        loanInfo.setMonthlyPayment(addLoanRecordDTO.getMonthlyPayment());
        loanInfo.setPastMonth(addLoanRecordDTO.getPastMonth());
        loanInfo.setLoanDuration(addLoanRecordDTO.getLoanDuration());
        loanInfo.setLoanDeadline(deadline);
        loanInfo.setIsFamilyLoan(1);
        loanInfo.setUserId(userId);
        loanInfoDao.addJointLoanRecord(loanInfo);
        /*获取家庭信息*/
        Long familyId = familyInfoDao.getFamilyIdByUserId(userId);
        return loanInfoDao.addFamilyLoanRecord(loanInfo.getLoanId(),familyId)>0;
    }

    @Override
    public boolean deleteLoanRecord(Long loanId) {
        return loanInfoDao.deleteLoanRecord(loanId)>0;
    }

    @Override
    public List<GetLoanRecordVO> getLoanRecordList(Integer isFinished,Long userId) {
        //展示全部贷款列表
        if(isFinished == null){
            return loanInfoDao.getAllLoanRecordList(userId);
        }
        //展示未还清贷款列表
        else if(isFinished==0){
            return loanInfoDao.getUnfinishedLoanRecordList(userId);
        }
        //展示已还清贷款列表
        else {
            return loanInfoDao.getFinishedLoanRecordList(userId);
        }
    }

    @Override
    public LoanDetailVO getLoanDetail(Long loanId) {
        return loanInfoDao.getLoanDetail(loanId);
    }

    @Override
    public List<GetLoanRecordVO> getFamilyLoanRecordList(Integer isFinished, Long userId) {
        List<GetLoanRecordVO>getFamilyLoanRecordList = new ArrayList<>();
        GetLoanRecordVO loanRecord = new GetLoanRecordVO();
        //获取familyId
        Long familyId = familyInfoDao.getFamilyIdByUserId(userId);
        List<Long> loans = familyInfoDao.getLoanIdByFamilyId(familyId);
        //展示全部贷款列表
        if(isFinished == null){
            for (Long loanId:loans) {
                loanRecord = loanInfoDao.getLoanById(loanId);
                getFamilyLoanRecordList.add(loanRecord);
            }
        }
        //展示未还清贷款列表
        else if(isFinished==0){
            for (Long loanId:loans) {
                loanRecord = loanInfoDao.getUnfinishedLoanById(loanId);
                getFamilyLoanRecordList.add(loanRecord);
            }
        }
        //展示已还清贷款列表
        else {
            for (Long loanId:loans) {
                loanRecord = loanInfoDao.getFinishedLoanById(loanId);
                getFamilyLoanRecordList.add(loanRecord);
            }
        }
        return getFamilyLoanRecordList;
    }

    @Override
    public FamilyLoanDetailVO getFamilyLoanDetail(Long loanId) {
        return loanInfoDao.getFamilyLoanDetail(loanId);
    }

    @Override
    public boolean deleteFamilyLoanRecord(Long loanId, Long userId) {
        Long creator = loanInfoDao.getCreatorByLoanId(loanId);
        //只有创建者才有权限删除贷款记录
        if(creator!=userId){
            return false;
        }
        return loanInfoDao.deleteLoanRecord(loanId)>0;
    }
}
