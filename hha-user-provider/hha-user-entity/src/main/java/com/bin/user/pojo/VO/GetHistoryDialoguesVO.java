package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/2 16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetHistoryDialoguesVO implements Serializable {
    private Long userId;
    private String userName;
    private String headImg;
    private String dialogueContent;
    private String createdTime;
}
