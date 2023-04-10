package com.bin.user.pojo.DTO;

import com.bin.user.pojo.PO.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/3 0:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoingDutchToFriendsDTO implements Serializable {
    private List<Friend> friends;
    private Long typeId;
    private String outcomeName;
    private float outcome;
    private Date date;
}
