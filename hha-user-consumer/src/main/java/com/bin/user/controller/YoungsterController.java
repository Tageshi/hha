package com.bin.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bin.common.core.api.ResponseResult;
import com.bin.common.core.utils.QiNiuUtils;
import com.bin.common.web.base.BaseController;
import com.bin.quartz.QuartzProvider;
import com.bin.user.api.*;
import com.bin.user.pojo.DTO.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bin.common.core.api.AppHttpCodeEnum.SUCCESS;
import static com.bin.common.core.api.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * @author tageshi
 * @date 2023/3/16 17:19
 */
@RestController
@RequestMapping("/youngster")
public class YoungsterController extends BaseController {

    @Reference
    private OutcomeService outcomeService;
    @Reference
    private IncomeService incomeService;
    @Reference
    private LoanService loanService;
    @Reference
    private FamilyService familyService;
    @Reference
    private FriendshipService friendshipService;
    @Reference
    private UserInfoService userInfoService;
    @Reference
    private AlgorithmService algorithmService;

    /**
     * @description:添加支出记录（记账）
     * @author: tageshi
     * @date: 2023/3/16 17:21
     **/
    @PostMapping("/addOutcomeRecord")
    public ResponseResult addOutcomeRecord(@Validated @RequestBody AddOutcomeRecordDTO addOutcomeRecordDTO){
        if(outcomeService.addOutcomeRecord(addOutcomeRecordDTO, getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查询消费类型列表
     * @author: tageshi
     * @date: 2023/3/26 21:25
     **/
    @GetMapping("/getTypeList")
    public ResponseResult getTypeList(){
        return ResponseResult.okResult(outcomeService.getTypeList(getUserId()));
    }
    /**
     * @description: 添加自定义消费类型
     * @author: tageshi
     * @date: 2023/3/26 20:33
     **/
    @PostMapping("/addConsumeType")
    public ResponseResult addConsumeType(@Validated @RequestBody AddConsumeTypeDTO addConsumeTypeDTO){
        if(outcomeService.addConsumeType(addConsumeTypeDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }
    /**
     * @description: 删除自定义消费类型
     * @author: tageshi
     * @date: 2023/3/26 21:26
     **/
    @PutMapping("/deleteConsumeType")
    public ResponseResult deleteConsumeType(@Validated Long typeId){
        if(outcomeService.deleteConsumeType(typeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 修改支出记录
     * @author: tageshi
     * @date: 2023/3/27 20:41
     **/
    @PutMapping("/updateOutcomeRecord")
    public ResponseResult updateOutcomeRecord(@Validated @RequestBody AddOutcomeRecordDTO addOutcomeRecordDTO,@Validated Long outcomeId){
        if(outcomeService.updateOutcomeRecord(addOutcomeRecordDTO,outcomeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 删除支出记录
     * @author: tageshi
     * @date: 2023/3/27 20:41
     **/
    @PutMapping("/deleteOutcomeRecord")
    public ResponseResult deleteOutcomeRecord(@Validated Long outcomeId){
        if(outcomeService.deleteOutcomeRecord(outcomeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description:添加固定消费路线
     * @author: tageshi
     * @date: 2023/3/27 21:12
     **/
    @PostMapping("/addConsumeRoutine")
    public ResponseResult addConsumeRoutine(@Validated @RequestBody AddConsumeRoutineDTO addConsumeRoutineDTO){
        //开启定时任务
        QuartzProvider quartzProvider = new QuartzProvider();
        switch (addConsumeRoutineDTO.getPeriod()){
            case "每天":
                quartzProvider.setCron("0 0 23 * * ? ");
                break;
            case "每周":
                quartzProvider.setCron("0 59 23 ? * SUN ");
                break;
            case "每月":
                quartzProvider.setCron("0 59 23 L * ? ");
                break;
            default:break;
        }
        if(quartzProvider.addQuartz() &&
                outcomeService.addConsumeRoutine(addConsumeRoutineDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查看固定消费路线列表
     * @author: tageshi 
     * @date: 2023/3/28 0:07
     **/
    @GetMapping("/getConsumeRoutineList")
    public ResponseResult getConsumeRoutineList(){
        return ResponseResult.okResult(outcomeService.getConsumeRoutineList(getUserId()));
    }

    /**
     * @description: 查看固定消费路线详情
     * @author: tageshi
     * @date: 2023/3/28 1:02
     **/
    @GetMapping("/getConsumeRoutineDetail")
    public ResponseResult getConsumeRoutineDetail(@Validated Long routineId){
        return ResponseResult.okResult(outcomeService.getConsumeRoutineDetail(routineId));
    }

    /**
     * @description: 修改固定消费路线
     * @author: tageshi
     * @date: 2023/3/28 10:07
     **/
    @PutMapping("/updateConsumeRoutine")
    public ResponseResult updateConsumeRoutine(@Validated @RequestBody AddConsumeRoutineDTO addConsumeRoutineDTO,@Validated Long routineId){
        if(outcomeService.updateConsumeRoutine(addConsumeRoutineDTO,routineId,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 删除固定消费路线
     * @author: tageshi
     * @date: 2023/3/28 10:51
     **/
    @PutMapping("/deleteConsumeRoutine")
    public ResponseResult deleteConsumeRoutine(@Validated Long routineId){
        if(outcomeService.deleteConsumeRoutine(routineId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }
    /**
     * @description: 按照固定消费路线记账
     * @author: tageshi
     * @date: 2023/3/28 16:58
     **/
    @PostMapping("/addRoutinedOutcome")
    public ResponseResult addRoutinedOutcome(@Validated @RequestBody AddRoutinedOutcomeDTO addRoutinedOutcomeDTO){
        return ResponseResult.okResult(outcomeService.addRoutinedOutcome(addRoutinedOutcomeDTO));
    }

    /**
     * @description: 获取固定消费场景列表（系统提供）
     * @author: tageshi
     * @date: 2023/3/30 21:25
     **/
    @GetMapping("/getConsumeSceneList")
    public ResponseResult getConsumeSceneList(){
        return ResponseResult.okResult(outcomeService.getConsumeSceneList());
    }

    /**
     * @description: 获取固定消费场景详情
     * @author: tageshi
     * @date: 2023/3/30 21:59
     **/
    @GetMapping("/getConsumeSceneDetail")
    public ResponseResult getConsumeSceneDetail(@Validated Long sceneId){
        return ResponseResult.okResult(outcomeService.getConsumeSceneDetail(sceneId));
    }

    @PostMapping("/addScenedOutcome")
    public ResponseResult addScenedOutcome(@Validated @RequestBody AddOutcomeRecordDTO addOutcomeRecordDTO){
        if(outcomeService.addOutcomeRecord(addOutcomeRecordDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查询近三月消费支出
     * @author: tageshi
     * @date: 2023/3/30 22:06
     **/
    @GetMapping("/getRecentOutcomes")
    public ResponseResult getRecentOutcomes(@Validated Long typeId){
        return ResponseResult.okResult(outcomeService.getRecentOutcomes(typeId,getUserId()));
    }

    /**
     * @description: 查询全部支出
     * @author: tageshi
     * @date: 2023/3/31 0:13
     **/
    @GetMapping("/getAllOutcomes")
    public ResponseResult getAllOutcomes(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                         Long typeId) {
        return ResponseResult.okResult(outcomeService.getAllOutcomes(startTime, endTime, typeId, getUserId()));
    }


    /**
     * @description: 添加固定收入
     * @author: tageshi
     * @date: 2023/3/31 16:40
     **/
    @PostMapping("/addFixedIncome")
    public ResponseResult addFixedIncome(@Validated @RequestBody AddIncomeRecordDTO addIncomeRecordDTO){
        //使用定时任务
        return ResponseResult.okResult();
    }

    /**
     * @description: 添加收入记录
     * @author: tageshi
     * @date: 2023/3/31 16:51
     **/
    @PostMapping("/addIncomeRecord")
    public ResponseResult addIncomeRecord(@Validated @RequestBody AddIncomeRecordDTO addIncomeRecordDTO){
        if(incomeService.addIncomeRecord(addIncomeRecordDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @PutMapping("/updateIncomeRecord")
    public ResponseResult updateIncomeRecord(@Validated @RequestBody AddIncomeRecordDTO addIncomeRecordDTO,@Validated Long incomeId){
        if(incomeService.updateIncomeRecord(addIncomeRecordDTO,incomeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @PutMapping("/deleteIncomeRecord")
    public ResponseResult deleteIncomeRecord(@Validated Long incomeId){
        if(incomeService.deleteIncomeRecord(incomeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getIncomeList")
    public ResponseResult getIncomeList(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                        Long typeId) {
        return ResponseResult.okResult(incomeService.getIncomeList(startTime, endTime, typeId,getUserId()));
    }


    @PostMapping("/addIncomePlan")
    public ResponseResult addIncomePlan(@Validated @RequestBody AddIncomePlanDTO addIncomePlanDTO) throws ParseException {
        if(incomeService.addIncomePlan(addIncomePlanDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @PutMapping("/deleteIncomePlan")
    public ResponseResult deleteIncomePlan(@Validated Long incomePlanId){
        if(incomeService.deleteIncomePlan(incomePlanId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查询收入规划（列表）
     * @author: tageshi
     * @date: 2023/3/31 21:49
     **/
    @GetMapping("/getIncomePlanList")
    public ResponseResult getIncomePlanList(@DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd) throws ParseException {
        return ResponseResult.okResult(incomeService.getIncomePlanList(dateStart,dateEnd,getUserId()));
    }

    @PostMapping("/addLoanRecord")
    public ResponseResult addLoanRecord(@Validated @RequestBody AddLoanRecordDTO addLoanRecordDTO){
        if(loanService.addLoanRecord(addLoanRecordDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @PutMapping("/deleteLoanRecord")
    public ResponseResult deleteLoanRecord(@Validated Long loanId){
        if(loanService.deleteLoanRecord(loanId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查看贷款列表
     * @author: tageshi
     * @date: 2023/4/1 1:11
     **/
    @GetMapping("/getLoanRecordList")
    public ResponseResult getLoanRecordList(@Validated Integer isFinished){
        return ResponseResult.okResult(loanService.getLoanRecordList(isFinished,getUserId()));
    }

    @GetMapping("/getLoanDetail")
    public ResponseResult getLoanDetail(@Validated Long loanId){
        return ResponseResult.okResult(loanService.getLoanDetail(loanId));
    }

    @PostMapping("/createFamily")
    public ResponseResult createFamily(@Validated @RequestBody CreateFamilyDTO createFamilyDTO){
        if(familyService.createFamily(createFamilyDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getFamilyMemberList")
    public ResponseResult getFamilyMemberList(){
        return ResponseResult.okResult(familyService.getFamilyMemberList(getUserId()));
    }

    @PutMapping("/updateFamilyInfo")
    public ResponseResult updateFamilyInfo(@Validated Long familyId,@Validated @RequestBody CreateFamilyDTO createFamilyDTO){
        if(familyService.updateFamilyInfo(familyId,createFamilyDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @PostMapping("/createJointExpenses")
    public ResponseResult createJointExpenses(@Validated @RequestBody AddOutcomeRecordDTO addOutcomeRecordDTO){
        if(familyService.createJointExpenses(addOutcomeRecordDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getJointExpenseList")
    public ResponseResult getJointExpenseList(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                              @Validated Long typeId) {
        return ResponseResult.okResult(familyService.getJointExpenseList(startTime,endTime,typeId,getUserId()));
    }

    @PutMapping("/deleteJointExpense")
    public ResponseResult deleteJointExpense(@Validated Long jointOutcomeId){
        if(familyService.deleteJointExpense(jointOutcomeId)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getAllFamilyBills")
    public ResponseResult getAllFamilyBills(@Validated Long memberUserId,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                            @Validated Long typeId){
        return ResponseResult.okResult(familyService.getAllFamilyBills(startTime,endTime,typeId,memberUserId,getUserId()));
    }

    @GetMapping("/calculateMonthlyExpenses")
    public ResponseResult calculateMonthlyExpenses(@Validated String dateString) throws ParseException {
        // 解析日期字符串为 Date 对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = dateFormat.parse(dateString);

        // 获取日历实例并设置时间为解析得到的 Date 对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 获取该月的第一天和最后一天的日期
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 设置日历日期为该月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        Date firstDayOfMonth = calendar.getTime();

        // 设置日历日期为该月的最后一天
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        Date lastDayOfMonth = calendar.getTime();

        return ResponseResult.okResult(outcomeService.getMonthlyExpenses(firstDayOfMonth,lastDayOfMonth,getUserId()));
    }

    @GetMapping("/calculateAnnuallyExpenses")
    public ResponseResult calculateAnnuallyExpenses(@Validated String dateString) throws ParseException{
        // 解析日期字符串为 Date 对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = dateFormat.parse(dateString);

        // 获取日历实例并设置时间为解析得到的 Date 对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 获取该年的第一天和最后一天的日期
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_YEAR);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        // 设置日历日期为该年的第一天
        calendar.set(Calendar.DAY_OF_YEAR, firstDay);
        Date firstDayOfYear = calendar.getTime();

        // 设置日历日期为该年的最后一天
        calendar.set(Calendar.DAY_OF_YEAR, lastDay);
        Date lastDayOfYear = calendar.getTime();

        return ResponseResult.okResult(outcomeService.getAnnuallyExpenses(firstDayOfYear,lastDayOfYear,getUserId()));
    }

    /**
     * @description: 获取家庭成员支出金额占比
     * @author: tageshi
     * @date: 2023/4/1 23:35
     **/
    @GetMapping("/getRatio")
    public ResponseResult getRatio(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){
        return ResponseResult.okResult(familyService.getRatio(startTime,endTime,getUserId()));
    }

    /**
     * @description: 获取家庭成员消费类型占比
     * @author: tageshi 
     * @date: 2023/4/2 0:35
     **/
    @GetMapping("/getTypeRatio")
    public ResponseResult getTypeRatio() {
        return ResponseResult.okResult(familyService.getTypeRatio(getUserId()));
    }

    @PostMapping("/createJointLoan")
    public ResponseResult createJointLoan(@Validated @RequestBody AddLoanRecordDTO addLoanRecordDTO){
        if (loanService.addJointLoanRecord(addLoanRecordDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getFamilyLoanList")
    public ResponseResult getFamilyLoanList(@Validated Integer isFinished){
        return ResponseResult.okResult(loanService.getFamilyLoanRecordList(isFinished,getUserId()));
    }

    @GetMapping("/getFamilyLoanDetail")
    public ResponseResult getFamilyLoanDetail(@Validated Long loanId){
        return ResponseResult.okResult(loanService.getFamilyLoanDetail(loanId));
    }

    @PutMapping("/deleteFamilyLoanRecord")
    public ResponseResult deleteFamilyLoanRecord(@Validated Long loanId){
        if(loanService.deleteFamilyLoanRecord(loanId,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 发送好友请求
     * @author: tageshi
     * @date: 2023/4/2 15:30
     **/
    @PostMapping("/sendFriendRequest")
    public ResponseResult sendFriendRequest(@Validated String phoneNumber){
        if(friendshipService.sendFriendRequest(phoneNumber,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查询好友请求列表
     * @author: tageshi
     * @date: 2023/4/2 15:39
     **/
    @GetMapping("/getRequestList")
    public ResponseResult getRequestList(@Validated Integer isAccepted){
        return ResponseResult.okResult(friendshipService.getRequestList(getUserId(),isAccepted));
    }

    @PostMapping("/acceptFriendRequest")
    public ResponseResult acceptFriendRequest(@Validated Long requestId){
        if(friendshipService.acceptFriendRequest(requestId,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getFriendsList")
    public ResponseResult getFriendsList(){
        return ResponseResult.okResult(friendshipService.getFriendsList(getUserId()));
    }

    @GetMapping("/getHistoryDialogues")
    public ResponseResult getHistoryDialogues(@Validated Long userId){
        return ResponseResult.okResult(friendshipService.getHistoryDialogues(userId,getUserId()));
    }

    @PostMapping("/sendEncouragementToFriend")
    public ResponseResult sendEncouragementToFriend(@Validated Long userId,@Validated String dialogueContent){
        if(friendshipService.sendEncouragementToFriend(userId,getUserId(),dialogueContent)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getFriendInfo")
    public ResponseResult getFriendInfo(@Validated Long userId){
        return ResponseResult.okResult(friendshipService.getFriendInfo(userId));
    }

    @PutMapping("/unfriend")
    private ResponseResult unfriend(@Validated Long userId){
        if (friendshipService.unfriend(userId,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 选择好友AA记账
     * @author: tageshi
     * @date: 2023/4/3 0:18
     **/
    @PostMapping("/goingDutchToFriends")
    public ResponseResult goingDutchToFriends(@Validated @RequestBody GoingDutchToFriendsDTO goingDutchToFriendsDTO){
        if(friendshipService.goingDutchToFriends(goingDutchToFriendsDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @PostMapping("/createGroup")
    public ResponseResult createGroup(@Validated @RequestBody CreateGroupDTO createGroupDTO) throws IOException {
        if(friendshipService.createGroup(createGroupDTO,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getGroupList")
    public ResponseResult getGroupList(){
        return ResponseResult.okResult(friendshipService.getGroupList(getUserId()));
    }

    @PostMapping("/goingDutchToGroup")
    public ResponseResult goingDutchToGroup(@Validated Long groupId,@Validated @RequestBody AddOutcomeRecordDTO addOutcomeRecordDTO){
        if(friendshipService.goingDutchToGroup(getUserId(),groupId,addOutcomeRecordDTO)){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getGroupHistoryDialogues")
    public ResponseResult getGroupHistoryDialogues(@Validated Long groupId){
        return ResponseResult.okResult(friendshipService.getGroupHistoryDialogues(groupId,getUserId()));
    }

    @GetMapping("/getGroupDetails")
    public ResponseResult getGroupDetails(@Validated Long groupId){
        return ResponseResult.okResult(friendshipService.getGroupDetails(groupId));
    }

    @PutMapping("/updateGroupInfo")
    public ResponseResult updateGroupInfo(@Validated Long groupId,
                                          @Validated String groupName,
                                          @Validated String groupImg){
        if(friendshipService.updateGroupInfo(groupId,groupName,groupImg,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @PutMapping("/deleteGroup")
    private ResponseResult deleteGroup(@Validated Long groupId){
        if(friendshipService.deleteGroup(groupId,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 根据支付宝账单记账
     * @author: tageshi
     * @date: 2023/4/4 0:21
     **/
    @PostMapping("/addOutcomeRecordByAlipayExcel")
    public ResponseResult addOutcomeRecordByAlipayExcel(@RequestParam("file") MultipartFile file){
        if(outcomeService.addOutcomeRecordByAlipayExcel(file,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    /**
     * @description: 查看个人信息
     * @author: tageshi
     * @date: 2023/4/5 15:53
     **/
    @GetMapping("/getFPersonalInfo")
    public ResponseResult getFPersonalInfo(){
        return ResponseResult.okResult(friendshipService.getFriendInfo(getUserId()));
    }


    /**
     * @description: 批量导入支出
     * @author: tageshi
     * @date: 2023/4/6 19:06
     **/
    @PostMapping("/addOutcomeRecordArray")
    public ResponseResult addOutcomeRecordArray(@Validated @RequestBody List<AddOutcomeRecordDTO> addOutcomeRecordDTOs){
        if(outcomeService.addOutcomeRecordArray(addOutcomeRecordDTOs,getUserId())){
            return ResponseResult.okResult(SUCCESS);
        }
        return ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/getPersonalInfo")
    public ResponseResult getPersonalInfo(){
        return ResponseResult.okResult(userInfoService.getUserInfoById(getUserId()));
    }

    /**
     * @description: 识别图片账单信息
     * @param: 传入一张base64格式图片（在方法体内识别出各项参数，根据这些数据完成记账）
     * @author: tageshi
     * @date: 2023/4/9 17:45
     **/
    @GetMapping("/identifyPicture")
    public ResponseResult identifyPicture(@RequestParam String fileName){
        String imageURL = QiNiuUtils.domain+"/"+fileName;
        return ResponseResult.okResult(algorithmService.identifyPicture(imageURL));
    }

    /**
     * @description: 返回一张图片中包含多张账单的情况
     * @author: tageshi
     * @date: 2023/4/10 14:03
     **/
    @GetMapping("/identifyPictureList")
    public ResponseResult identifyPictureList(@RequestParam String fileName){
        String imageURL = QiNiuUtils.domain+"/"+fileName;
        return ResponseResult.okResult(algorithmService.identifyPictures(imageURL));
    }

    /**
     * @description: 识别手写账单信息
     * @param: 将手写保存为一张图片，传输图片给后端
     * @author: tageshi
     * @date: 2023/4/9 18:46
     **/
    @GetMapping("/identifyHandwriting")
    public ResponseResult identifyHandwriting(@RequestParam String fileName){
        String imageURL = QiNiuUtils.domain+"/"+fileName;
        return ResponseResult.okResult(algorithmService.identifyHandwriting(imageURL));
    }

    /**
     * @description: 识别语音信息
     * @author: tageshi
     * @date: 2023/4/9 18:47
     **/
    @GetMapping("/identifyVoiceInfo")
    public ResponseResult identifyVoiceInfo(@RequestParam String fileName){
        String fileURL = QiNiuUtils.domain+"/"+fileName;
        return ResponseResult.okResult(algorithmService.identifyVoiceInfo(fileURL));
    }
}