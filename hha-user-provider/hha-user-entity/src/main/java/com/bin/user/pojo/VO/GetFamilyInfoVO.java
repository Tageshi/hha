package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/5 21:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFamilyInfoVO implements Serializable {
    private String familyName;
    private List<GetFamilyMemberVO> members;
}
