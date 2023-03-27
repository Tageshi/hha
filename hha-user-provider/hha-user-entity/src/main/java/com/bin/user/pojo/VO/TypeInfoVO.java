package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/26 21:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeInfoVO implements Serializable {
    private Long typeId;
    private String typeName;
    private String typeIcon;
}
