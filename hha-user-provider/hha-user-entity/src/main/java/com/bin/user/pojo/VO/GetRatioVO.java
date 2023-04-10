package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/1 23:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRatioVO implements Serializable {
    List<MemberRatioVO> memberRatios;
}