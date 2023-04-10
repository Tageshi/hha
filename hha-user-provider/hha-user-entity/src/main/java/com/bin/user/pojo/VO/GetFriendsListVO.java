package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author tageshi
 * @date 2023/4/2 16:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFriendsListVO implements Serializable {
    private Long userId;
    private String userName;
    private String headImg;
    private String lastDialogue;
    private String lastDialogueTime;

    public static Comparator<GetFriendsListVO> COMPARE_BY_LAST_DIALOGUE_TIME = new Comparator<GetFriendsListVO>() {
        public int compare(GetFriendsListVO one, GetFriendsListVO other) {
            if (one == null || one.getLastDialogueTime() == null) {
                return -1;
            } else if (other == null || other.getLastDialogueTime() == null) {
                return 1;
            } else {
                return one.getLastDialogueTime().compareTo(other.getLastDialogueTime());
            }
        }
    };

}
