package com.bin.user.pojo.DTO;

import com.bin.user.pojo.PO.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/3 0:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDTO implements Serializable {
    private List<Friend> users;
    private String groupName;
}
