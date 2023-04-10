package com.bin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.user.pojo.PO.Friendship;
import com.bin.user.pojo.PO.GroupInfo;
import com.bin.user.pojo.VO.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/2 13:59
 */
public interface FriendshipDao extends BaseMapper<Friendship> {
    /*注意friendUserId和userId的对应参数*/
    Integer sendFriendRequest(@Param("friendUserId")Long friendUserId,@Param("userId")Long userId);
    List<GetRequestVO> getRequestList(@Param("userId")Long userId, @Param("isAccepted")Integer isAccepted);
    Integer updateFriendRequestStatus(@Param("requestId")Long requestId);
    Long getFriendUserIdByRequestId(@Param("requestId")Long requestId);
    Integer addNewFriend(@Param("userId")Long userId, @Param("friendUserId")Long friendUserId);

    List<GetFriendsListVO> getFriendsList(@Param("userId")Long userId);
    List<GetHistoryDialoguesVO> getHistoryDialogues(@Param("friendUserId")Long friendUserId,@Param("userId")Long userId);
    Integer sendEncouragementToFriend(@Param("friendUserId")Long friendUserId,
                                      @Param("userId")Long userId,
                                      @Param("dialogueContent")String dialogueContent);
    Integer sendDialogueToGroup(@Param("groupId")Long groupId,
                                @Param("userId")Long userId,
                                @Param("dialogueContent")String dialogueContent);
    Integer unfriend(@Param("friendUserId")Long friendUserId,
                     @Param("userId")Long userId);
    @Options(useGeneratedKeys = true, keyProperty = "groupId", keyColumn = "group_id")
    @Insert("insert into group_info(group_name,group_leader,group_img)values (#{groupInfo.groupName},#{groupInfo.groupLeader},#{groupInfo.groupImg})")
    Integer createGroup(@Param("groupInfo") GroupInfo groupInfo);
    Integer insertGroupMember(@Param("groupId")Long groupId,@Param("userId")Long userId);
    List<GetGroupListVO> getGroupList(@Param("userId")Long userId);
    List<Long> getGroupMemberUserIdByGroupId(@Param("groupId")Long groupId);
    Long getGroupLeaderByGroupId(@Param("groupId")Long groupId);
    GroupInfo getGroupInfoById(@Param("groupId")Long groupId);
    List<GetHistoryDialoguesVO> getGroupHistoryDialogues(@Param("groupId")Long groupId);
    List<GroupMemberVO> getGroupMembers(@Param("groupId")Long groupId);
    Integer updateGroupInfo(@Param("groupId")Long groupId,
                            @Param("groupName")String groupName,
                            @Param("groupImg")String groupImg);
    Integer deleteGroupUsers(@Param("groupId")Long groupId);
    Integer deleteGroup(@Param("groupId")Long groupId);
}
