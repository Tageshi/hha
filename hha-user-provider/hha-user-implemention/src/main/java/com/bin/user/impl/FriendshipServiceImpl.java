package com.bin.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bin.common.core.utils.ImageUtils;
import com.bin.user.api.FriendshipService;
import com.bin.user.dao.FriendshipDao;
import com.bin.user.dao.OutcomeInfoDao;
import com.bin.user.dao.UserInfoDao;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.DTO.CreateGroupDTO;
import com.bin.user.pojo.DTO.GoingDutchToFriendsDTO;
import com.bin.user.pojo.PO.Friend;
import com.bin.user.pojo.PO.Friendship;
import com.bin.user.pojo.PO.GroupInfo;
import com.bin.user.pojo.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/2 14:01
 */
@Service
@Transactional
public class FriendshipServiceImpl extends ServiceImpl<FriendshipDao, Friendship> implements FriendshipService {
    @Autowired
    private FriendshipDao friendshipDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private OutcomeInfoDao outcomeInfoDao;

    /**
     * @description: 发送好友请求
     * @author: tageshi
     * @date: 2023/4/2 15:25
     **/
    @Override
    public boolean sendFriendRequest(String phoneNumber, Long userId) {
        //获取对方的userId
        Long friendUserId = userInfoDao.getUserInfoByPhoneNumber(phoneNumber).getUserId();
        //发送好友请求（userId是自己，friendUserId是对方）
        return friendshipDao.sendFriendRequest(userId,friendUserId)>0;
    }

    @Override
    public List<GetRequestVO> getRequestList(Long userId, Integer isAccepted) {
        //默认查询未处理的请求
        if(isAccepted==null){
            isAccepted=0;
        }
        return friendshipDao.getRequestList(userId,isAccepted);
    }

    @Override
    public boolean acceptFriendRequest(Long requestId, Long userId) {
        //将request置为已通过状态
        friendshipDao.updateFriendRequestStatus(requestId);
        //获取对方的userId
        Long friendUserId = friendshipDao.getFriendUserIdByRequestId(requestId);
        //添加两条好友记录
        return friendshipDao.addNewFriend(userId,friendUserId)>0 && friendshipDao.addNewFriend(friendUserId,userId)>0;
    }

    @Override
    public List<GetFriendsListVO> getFriendsList(Long userId) {
        List<GetFriendsListVO> resultList = friendshipDao.getFriendsList(userId);
        //按照对话时间对list中的每一项进行排序
        Collections.sort(resultList, GetFriendsListVO.COMPARE_BY_LAST_DIALOGUE_TIME);
        return resultList;
    }

    @Override
    public List<GetHistoryDialoguesVO> getHistoryDialogues(Long friendUserId, Long userId) {
        return friendshipDao.getHistoryDialogues(friendUserId,userId);
    }

    @Override
    public boolean sendEncouragementToFriend(Long friendUserId,Long userId, String dialogueContent) {
        //发送消息
        //加分
        String phoneNumber = userInfoDao.getPhoneNumberByUserId(userId);
        return userInfoDao.updateScore(phoneNumber)>0 && friendshipDao.sendEncouragementToFriend(friendUserId,userId,dialogueContent)>0;
    }

    @Override
    public GetFriendInfoVO getFriendInfo(Long userId) {
        return userInfoDao.getFriendInfo(userId);
    }

    @Override
    public boolean unfriend(Long friendUserId, Long userId) {
        //更新数据表friendship
        return friendshipDao.unfriend(friendUserId,userId)>0 && friendshipDao.unfriend(userId,friendUserId)>0;
    }

    @Override
    public boolean goingDutchToFriends(GoingDutchToFriendsDTO goingDutchToFriendsDTO, Long userId) {
        List<Friend> friends = goingDutchToFriendsDTO.getFriends();
        boolean flag = true;
        if(outcomeInfoDao.insertOutcomeRecord(goingDutchToFriendsDTO.getTypeId(),
                goingDutchToFriendsDTO.getOutcomeName(),goingDutchToFriendsDTO.getOutcome()/(friends.size()+1),
                goingDutchToFriendsDTO.getDate(),userId)<=0){
            flag = false;
        }
        for (Friend friend:friends) {
            if(outcomeInfoDao.insertOutcomeRecord(goingDutchToFriendsDTO.getTypeId(),
                    goingDutchToFriendsDTO.getOutcomeName(),goingDutchToFriendsDTO.getOutcome()/(friends.size()+1),
                    goingDutchToFriendsDTO.getDate(),friend.getUserId())<=0){
                flag = false;
            }
            friendshipDao.sendEncouragementToFriend(friend.getUserId(),userId,"AA记账通知："+goingDutchToFriendsDTO.getOutcomeName()+"\n" +"支出总额："+goingDutchToFriendsDTO.getOutcome()+"\n"+"均摊人数："+(friends.size()+1)+"\n" +
                    "均摊金额："+goingDutchToFriendsDTO.getOutcome()/(friends.size()+1)+"\n"+"消费类型："+outcomeInfoDao.getTypeName(goingDutchToFriendsDTO.getTypeId())+"\n"+
                    "消费日期："+goingDutchToFriendsDTO.getDate());
        }
        return flag;
    }

    @Override
    public boolean createGroup(CreateGroupDTO createGroupDTO, Long userId) throws IOException {
        boolean flag = true;
        List<Friend> members = createGroupDTO.getUsers();
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupName(createGroupDTO.getGroupName());
        groupInfo.setGroupLeader(userId);
        //插入群头像
        groupInfo.setGroupImg(ImageUtils.createGroupImg(createGroupDTO.getGroupName(),userId));
        if(friendshipDao.createGroup(groupInfo)<=0){
            flag = false;
        }
        if(friendshipDao.insertGroupMember(groupInfo.getGroupId(),userId)<=0){
            flag = false;
        }
        for (Friend member:members) {
            if(friendshipDao.insertGroupMember(groupInfo.getGroupId(),member.getUserId())<=0){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public List<GetGroupListVO> getGroupList(Long userId) {
        List<GetGroupListVO>resultList = friendshipDao.getGroupList(userId);
        //按照对话时间对list中的每一项进行排序
        Collections.sort(resultList, GetGroupListVO.COMPARE_BY_LAST_DIALOGUE_TIME);
        return resultList;
    }

    @Override
    public boolean goingDutchToGroup(Long userId, Long groupId, AddOutcomeRecordDTO addOutcomeRecordDTO) {
        boolean flag = true;
        List<Long>members = friendshipDao.getGroupMemberUserIdByGroupId(groupId);
        String groupName = friendshipDao.getGroupInfoById(groupId).getGroupName();
        if(friendshipDao.sendDialogueToGroup(groupId,userId,"群组记账通知："+addOutcomeRecordDTO.getOutcomeName()+"\n" +
                "群组名称："+groupName+"\n" +"群组人数："+members.size()+
                "\n" +"支出总额："+addOutcomeRecordDTO.getOutcome()+"\n"+
                "均摊金额："+addOutcomeRecordDTO.getOutcome()/members.size()+"\n"+"消费类型："+outcomeInfoDao.getTypeName(addOutcomeRecordDTO.getTypeId())+"\n"+
                "消费日期："+addOutcomeRecordDTO.getDate())<=0){
            flag = false;
        }
        for (Long memberUserId:members) {
            if(outcomeInfoDao.insertOutcomeRecord(addOutcomeRecordDTO.getTypeId(),addOutcomeRecordDTO.getOutcomeName(),
                    addOutcomeRecordDTO.getOutcome()/members.size(),addOutcomeRecordDTO.getDate(),memberUserId)<=0){
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public GetGroupHistoryDialoguesVO getGroupHistoryDialogues(Long groupId, Long userId) {
        GroupInfo groupInfo = friendshipDao.getGroupInfoById(groupId);
        List<GetHistoryDialoguesVO> getHistoryDialogues = friendshipDao.getGroupHistoryDialogues(groupId);
        GetGroupHistoryDialoguesVO getGroupHistoryDialoguesVO = new GetGroupHistoryDialoguesVO();
        getGroupHistoryDialoguesVO.setGroupId(groupId);
        getGroupHistoryDialoguesVO.setGroupImg(groupInfo.getGroupImg());
        getGroupHistoryDialoguesVO.setGroupLeader(groupInfo.getGroupLeader()); //leader的userId
        getGroupHistoryDialoguesVO.setGroupName(groupInfo.getGroupName());
        getGroupHistoryDialoguesVO.setGroupDialogues(getHistoryDialogues);
        return getGroupHistoryDialoguesVO;
    }

    @Override
    public GetGroupDetailsVO getGroupDetails(Long groupId) {
        GroupInfo groupInfo = friendshipDao.getGroupInfoById(groupId);
        GetGroupDetailsVO getGroupDetails = new GetGroupDetailsVO();
        getGroupDetails.setGroupId(groupId);
        getGroupDetails.setGroupImg(groupInfo.getGroupImg());
        getGroupDetails.setGroupLeader(groupInfo.getGroupLeader());
        getGroupDetails.setGroupName(groupInfo.getGroupName());
        getGroupDetails.setGroupMembers(friendshipDao.getGroupMembers(groupId));
        return getGroupDetails;
    }

    @Override
    public boolean updateGroupInfo(Long groupId, String groupName,String groupImg, Long userId) {
        Long groupLeader = friendshipDao.getGroupLeaderByGroupId(groupId);
        if(groupLeader!=userId){
            return false;
        }
        return friendshipDao.updateGroupInfo(groupId,groupName,groupImg)>0;
    }

    @Override
    public boolean deleteGroup(Long groupId, Long userId) {
        Long groupLeader = friendshipDao.getGroupLeaderByGroupId(groupId);
        if(groupLeader!=userId){
            return false;
        }
        boolean flag = true;
        //删除group_user
        if(friendshipDao.deleteGroupUsers(groupId)<=0){
            flag = false;
        }
        //删除group_info
        if(friendshipDao.deleteGroup(groupId)<=0){
            flag = false;
        }
        return flag;
    }
}
