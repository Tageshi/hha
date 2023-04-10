package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/1 21:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllFamilyBillsVO implements Serializable {
    private Long outcomeId;
    private String outcomeName;
    private Long typeId;
    private String typeName;
    private String typeIcon;
    private Long memberUserId;
    private String memberUserName;
    private Date date;
}
