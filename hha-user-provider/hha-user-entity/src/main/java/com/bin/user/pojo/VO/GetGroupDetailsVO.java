package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/3 1:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGroupDetailsVO implements Serializable {
    private Long groupId;
    private String groupName;
    private String groupImg;
    private Long groupLeader;
    private List<GroupMemberVO> groupMembers;
}
