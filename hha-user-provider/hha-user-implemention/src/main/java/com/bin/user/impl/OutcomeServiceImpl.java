package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.user.api.OutcomeService;
import com.bin.user.dao.IncomeInfoDao;
import com.bin.user.dao.OutcomeInfoDao;
import com.bin.user.pojo.DTO.*;
import com.bin.user.pojo.PO.*;
import com.bin.user.pojo.VO.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * @author tageshi
 * @date 2023/3/17 13:37
 */
@Service
@Transactional
public class OutcomeServiceImpl extends ServiceImpl<OutcomeInfoDao, OutcomeInfo> implements OutcomeService {
    @Autowired
    private OutcomeInfoDao outcomeInfoDao;
    @Autowired
    private IncomeInfoDao incomeInfoDao;
    /**
     * @description: 添加支出（记账）
     * @author: tageshi
     * @date: 2023/3/26 20:25
     **/
    @Override
    public boolean addOutcomeRecord(AddOutcomeRecordDTO addOutcomeRecordDTO,Long userId) {
        return outcomeInfoDao.insertOutcomeRecord(addOutcomeRecordDTO.getTypeId(),
                addOutcomeRecordDTO.getOutcomeName(),
                addOutcomeRecordDTO.getOutcome(),
                addOutcomeRecordDTO.getDate(),userId)>0;
    }


    /**
     * @description: 查询消费类型列表
     * @author: tageshi
     * @date: 2023/3/26 21:14
     **/
    @Override
    public List<TypeInfoVO> getTypeList(Long userId) {
        //查询系统提供的消费类型
        List<TypeInfoVO>systemList = outcomeInfoDao.getTypeList();//目前只定义了id=2的类型为系统消费类型
        //查询自定义消费类型
        List<TypeInfoVO>definedList = outcomeInfoDao.getDefinedTypeList(userId);
        List<TypeInfoVO>list=new ArrayList<>();
        list.addAll(systemList);
        list.addAll(definedList);
        return list;
    }

    /**
     * @description: 添加自定义类型
     * @author: tageshi
     * @date: 2023/3/26 20:26
     **/
    @Override
    public boolean addConsumeType(AddConsumeTypeDTO addConsumeTypeDTO,Long userId) {
        //对type_info和user_type同时操作
        TypeInfo type = new TypeInfo();
        type.setTypeName(addConsumeTypeDTO.getTypeName());
        type.setTypeIcon(addConsumeTypeDTO.getTypeIcon());
        outcomeInfoDao.insertDefinedType(type);
        return outcomeInfoDao.insertUserType(userId, type.getTypeId())>0;
    }

    /**
     * @description: 删除自定义类型
     * @author: tageshi
     * @date: 2023/3/26 21:29
     **/
    @Override
    public boolean deleteConsumeType(Long typeId) {
        return outcomeInfoDao.deleteCascadeType(typeId)>0 && outcomeInfoDao.deleteOneType(typeId)>0;
    }

    /**
     * @description: 修改支出记录
     * @author: tageshi
     * @date: 2023/3/27 20:42
     **/
    @Override
    public boolean updateOutcomeRecord(AddOutcomeRecordDTO addOutcomeRecordDTO, Long outcomeId) {
        return outcomeInfoDao.updateOutcomeRecord(outcomeId,addOutcomeRecordDTO.getTypeId(),addOutcomeRecordDTO.getOutcomeName(),
                addOutcomeRecordDTO.getOutcome(),addOutcomeRecordDTO.getDate())>0;
    }

    /**
     * @description: 删除支出记录
     * @author: tageshi
     * @date: 2023/3/27 20:42
     **/
    @Override
    public boolean deleteOutcomeRecord(Long outcomeId) {
        return outcomeInfoDao.deleteOutcomeRecord(outcomeId)>0;
    }

    /**
     * @description: 添加固定路线
     * @author: tageshi
     * @date: 2023/3/27 21:19
     **/
    @Override
    public boolean addConsumeRoutine(AddConsumeRoutineDTO addConsumeRoutineDTO,Long userId) {
        //获取detail数组
        List<RoutineDetail> routineDetails = addConsumeRoutineDTO.getRoutineDetail();
        //初始化消费类型名称和icon
        String name = "";
        String icon = "";
        //封装routine对象
        RoutineInfo routine = new RoutineInfo();
        routine.setRoutineName(addConsumeRoutineDTO.getRoutineName());
        routine.setRoutineIcon(addConsumeRoutineDTO.getRoutineIcon());
        //插入路线
        if(outcomeInfoDao.addConsumeRoutine(routine,userId)>0){
            //插入路线详情
            for (RoutineDetail rd:routineDetails) {
                //获取消费类型名称和icon
                icon = outcomeInfoDao.getTypeIcon(rd.getDetailTypeId());
                name = outcomeInfoDao.getTypeName(rd.getDetailTypeId());
                outcomeInfoDao.addRoutineDetail(rd.getDetailName(),rd.getDetailTypeId(),name,icon,rd.getDetailCost(),routine.getRoutineId());
            }
            return true;
        }
        return false;
    }

    /**
     * @description: 查看固定消费路线列表
     * @author: tageshi 
     * @date: 2023/3/28 0:10
     **/
    @Override
    public List<RoutineInfoVO> getConsumeRoutineList(Long userId) {
        return outcomeInfoDao.getRoutineList(userId);
    }

    /**
     * @description: 查看固定消费路线详情
     * @author: tageshi
     * @date: 2023/3/28 0:44
     **/
    @Override
    public RoutineDetailVO getConsumeRoutineDetail(Long routineId) {
        //封装routine对象
        RoutineDetailVO routineDetailVO = new RoutineDetailVO();
        RoutineInfoVO routine = outcomeInfoDao.getRoutineInfo(routineId);
        routineDetailVO.setRoutineName(routine.getRoutineName());
        routineDetailVO.setRoutineIcon(routine.getRoutineIcon());
        routineDetailVO.setRoutineDetail(outcomeInfoDao.getRoutineItem(routineId));
        return routineDetailVO;
    }

    /**
     * @description: 修改固定消费路线
     * @author: tageshi
     * @date: 2023/3/28 10:24
     **/
    @Override
    public boolean updateConsumeRoutine(AddConsumeRoutineDTO addConsumeRoutineDTO, Long routineId,Long userId) {
        //获取detail数组
        List<RoutineDetail> routineDetails = addConsumeRoutineDTO.getRoutineDetail();
        //初始化消费类型名称和icon
        String name = "";
        String icon = "";
        //封装routine对象
        RoutineInfo routine = new RoutineInfo();
        routine.setRoutineName(addConsumeRoutineDTO.getRoutineName());
        routine.setRoutineIcon(addConsumeRoutineDTO.getRoutineIcon());
        //先执行routine_info的修改
        if(outcomeInfoDao.updateConsumeRoutine(routineId,
                addConsumeRoutineDTO.getRoutineName(),addConsumeRoutineDTO.getRoutineIcon())<=0){
            return false;
        }
        //再执行routine_detail的删除
        if (outcomeInfoDao.deleteRoutineDetail(routineId)<=0){
            return false;
        }
        //后执行routine_detail的更新
        //插入路线详情
        for (RoutineDetail rd:routineDetails) {
            //获取消费类型名称和icon
            icon = outcomeInfoDao.getTypeIcon(rd.getDetailTypeId());
            name = outcomeInfoDao.getTypeName(rd.getDetailTypeId());
            if (outcomeInfoDao.addRoutineDetail(rd.getDetailName(),
                    rd.getDetailTypeId(),name,icon,rd.getDetailCost(),routineId)<=0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean deleteConsumeRoutine(Long routineId) {
        return outcomeInfoDao.deleteConsumeRoutine(routineId)>0 && outcomeInfoDao.deleteRoutineDetail(routineId)>0;
    }

    /**
     * @description: 定时任务添加固定路线支出
     * @author: tageshi
     * @date: 2023/3/28 17:37
     **/
    @Override
    public boolean addRoutinedOutcome(AddRoutinedOutcomeDTO addRoutinedOutcomeDTO) {
        //定时任务，没写
        return true;
    }

    /**
     * @description: 获取固定消费场景列表
     * @author: tageshi
     * @date: 2023/3/30 21:27
     **/
    @Override
    public List<GetConsumeSceneVO> getConsumeSceneList() {
        return outcomeInfoDao.getConsumeSceneList();
    }

    @Override
    public GetConsumeSceneDetailVO getConsumeSceneDetail(Long sceneId) {
        GetConsumeSceneDetailVO getConsumeSceneDetailVO = new GetConsumeSceneDetailVO();
        getConsumeSceneDetailVO.setSceneName(outcomeInfoDao.getSceneName(sceneId));
        getConsumeSceneDetailVO.setDetails(outcomeInfoDao.getConsumeSceneDetail(sceneId));
        return getConsumeSceneDetailVO;
    }

    /**
     * @description: 查询近三月全部支出
     * @author: tageshi
     * @date: 2023/3/31 0:14
     **/
    @Override
    public List<OutcomeRecordVO> getRecentOutcomes(Long typeId, Long userId) {
        //获取前三个月的起始时间
        Date dateNow = new Date();   //当前时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dateNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -3);  //设置为前3月
        Date dateBefore = calendar.getTime();   //得到前3月的时间
        return outcomeInfoDao.getRecentOutcomes(typeId,userId,dateBefore);
    }

    /**
     * @description: 查询全部支出
     * @author: tageshi
     * @date: 2023/3/31 0:15
     **/
    @Override
    public List<OutcomeRecordVO> getAllOutcomes(Date startTime, Date endTime, Long typeId, Long userId) {
        return outcomeInfoDao.getAllOutcomes(startTime,endTime,typeId,userId);
    }

    /**
     * @description: 计算月度账单数据
     * @author: tageshi
     * @date: 2023/4/1 22:41
     **/
    @Override
    public MonthlyExpensesVO getMonthlyExpenses(Date startTime,Date endTime,Long userId) {
        MonthlyExpensesVO monthlyExpensesVO = new MonthlyExpensesVO();
        Float totalCost = outcomeInfoDao.getMonthlyTotalCost(startTime,endTime,userId);
        List<CostDetail> costDetails = outcomeInfoDao.getMonthlyCostDetail(startTime,endTime,userId);
        if(totalCost!=null){
            monthlyExpensesVO.setTotalCost(totalCost);
        }
        monthlyExpensesVO.setCostDetail(costDetails);
        return monthlyExpensesVO;
    }

    @Override
    public AnnuallyExpensesVO getAnnuallyExpenses(Date startTime, Date endTime, Long userId) {
        AnnuallyExpensesVO annuallyExpensesVO = new AnnuallyExpensesVO();
        Float totalCost = outcomeInfoDao.getAnnuallyTotalCost(startTime,endTime,userId);
        List<CostDetail> costDetails = outcomeInfoDao.getAnnuallyCostDetail(startTime,endTime,userId);
        if(totalCost!=null){
            annuallyExpensesVO.setTotalCost(totalCost);
        }
        annuallyExpensesVO.setCostDetail(costDetails);
        return annuallyExpensesVO;
    }

    /**
     * @description: 导入支付宝账单文件记账
     * @author: tageshi
     * @date: 2023/4/4 0:25
     **/
    @Override
    public boolean addOutcomeRecordByAlipayExcel(MultipartFile file,Long userId) {

        if (file.isEmpty()) {
            return false;
        }
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);

            // 获取第一个sheet
            Sheet sheet = workbook.getSheetAt(0);
            // 获取每个数据所在的列
            int typeColumnIndex = 0; // 收/支在第1列
            int descColumnIndex = 3; // 商品说明在第4列
            int amountColumnIndex = 5; // 金额在第6列
            int categoryColumnIndex = 7; // 交易分类在第8列
            int timeColumnIndex = 10; // 交易时间在第11列

            // 读取数据行，从第3行开始
            for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                // 读取单元格数据
                Cell typeCell = row.getCell(typeColumnIndex);
                String type = typeCell == null ? "" : typeCell.toString(); //“收”或“支”
                Cell descCell = row.getCell(descColumnIndex);
                String desc = descCell == null ? "" : descCell.toString();
                Cell amountCell = row.getCell(amountColumnIndex);
                float amount = amountCell == null ? 0 : (float) amountCell.getNumericCellValue();
                Cell categoryCell = row.getCell(categoryColumnIndex);
                String category = categoryCell == null ? "" : categoryCell.toString();
                Cell timeCell = row.getCell(timeColumnIndex);
                Date date = timeCell == null ? null : timeCell.getDateCellValue();

                Long typeId;
                switch (category) {
                    case "餐饮美食":
                        typeId = (long)1;
                        break;
                    case "烟酒支出":
                        typeId = (long)2;
                        break;
                    case "数码电器":
                    case "交通出行":
                    case "爱车养车":
                        typeId = (long)3;
                        break;
                    case "运动户外":
                        typeId = (long)4;
                        break;
                    case "服饰装扮":
                    case "美容美发":
                        typeId = (long)5;
                        break;
                    case "家居家装":
                    case "住房物业":
                    case "酒店民宿支出":
                        typeId = (long)6;
                        break;
                    case "日用百货":
                    case "生活服务":
                    case "公共服务":
                    case "商业服务":
                    case "充值缴费":
                        typeId = (long)7;
                        break;
                    case "医疗健康":
                        typeId = (long)8;
                        break;
                    case "文化休闲'":
                    case "教育培训":
                    case "投资理财":
                    case "保险":
                        typeId = (long)9;
                        break;
                    default:
                        typeId = (long)10;
                        break;
                }
                if(type.equals("支出")){
                    outcomeInfoDao.insertOutcomeRecord(typeId,desc,amount,date,userId);
                }
                else if(type.equals("收入")){
                    incomeInfoDao.insertIncomeRecord(typeId,desc,amount,date,userId);
                }
            }
            // 关闭工作簿
            workbook.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @description: 批量导入支出
     * @author: tageshi
     * @date: 2023/4/6 19:08
     **/
    @Override
    public boolean addOutcomeRecordArray(List<AddOutcomeRecordDTO> addOutcomeRecordDTOs, Long userId) {
        boolean flag = true;
        for (AddOutcomeRecordDTO outcomeRecord:addOutcomeRecordDTOs) {
            if(outcomeInfoDao.insertOutcomeRecord(outcomeRecord.getTypeId(),outcomeRecord.getOutcomeName(),outcomeRecord.getOutcome(),
                    outcomeRecord.getDate(),userId)<=0){
                flag = false;
            }
        }
        return flag;
    }


}
