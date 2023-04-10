package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author tageshi
 * @date 2023/4/3 0:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGroupListVO implements Serializable {
    private Long groupId;
    private String groupName;
    private String groupImg;
    private String lastDialogue;
    private Long lastDialogueSender;
    private String lastDialogueSenderName;
    private String lastDialogueSenderHeadImg;
    private String lastDialogueTime;
    public static Comparator<GetGroupListVO> COMPARE_BY_LAST_DIALOGUE_TIME = new Comparator<GetGroupListVO>() {
        public int compare(GetGroupListVO one, GetGroupListVO other) {
            return one.getLastDialogueTime().compareTo(other.getLastDialogueTime());
        }
    };
}
