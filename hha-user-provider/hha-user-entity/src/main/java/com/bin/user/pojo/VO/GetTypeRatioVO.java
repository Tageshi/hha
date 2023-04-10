package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/2 0:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTypeRatioVO implements Serializable {
    private Long typeId;
    private String typeName;
    private String typeIcon;
    private float ratio;
}
