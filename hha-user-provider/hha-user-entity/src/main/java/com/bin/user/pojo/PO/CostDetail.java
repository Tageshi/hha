package com.bin.user.pojo.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/1 22:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostDetail implements Serializable {
    private Long typeId;
    private String typeName;
    private String typeIcon;
    private float cost;
}
