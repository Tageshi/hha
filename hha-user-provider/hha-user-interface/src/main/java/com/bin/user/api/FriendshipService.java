package com.bin.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bin.user.pojo.DTO.AddOutcomeRecordDTO;
import com.bin.user.pojo.DTO.CreateGroupDTO;
import com.bin.user.pojo.DTO.GoingDutchToFriendsDTO;
import com.bin.user.pojo.PO.Friendship;
import com.bin.user.pojo.VO.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/2 14:01
 */
public interface FriendshipService extends IService<Friendship> {
    public boolean sendFriendRequest(String phoneNumber,Long userId);
    public List<GetRequestVO> getRequestList(Long userId,Integer isAccepted);
    public boolean acceptFriendRequest(Long requestId,Long userId);
    //只查找最近的一条会话展示
    public List<GetFriendsListVO>getFriendsList(Long userId);
    public List<GetHistoryDialoguesVO>getHistoryDialogues(Long friendUserId,Long userId);
    public boolean sendEncouragementToFriend(Long friendUserId,Long userId, String dialogueContent);
    public GetFriendInfoVO getFriendInfo(Long userId);
    public boolean unfriend(Long friendUserId,Long userId);
    public boolean goingDutchToFriends(GoingDutchToFriendsDTO goingDutchToFriendsDTO,Long userId);
    public boolean createGroup(CreateGroupDTO createGroupDTO,Long userId) throws IOException;
    public List<GetGroupListVO>getGroupList(Long userId);
    public boolean goingDutchToGroup(Long userId, Long groupId,AddOutcomeRecordDTO addOutcomeRecordDTO);
    public GetGroupHistoryDialoguesVO getGroupHistoryDialogues(Long groupId,Long userId);
    public GetGroupDetailsVO getGroupDetails(Long groupId);
    public boolean updateGroupInfo(Long groupId,String groupName,String groupImg,Long userId);
    public boolean deleteGroup(Long groupId,Long userId);
}
