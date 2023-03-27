package com.bin.user.pojo.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/27 21:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDetail implements Serializable {
    private String detailName;
    private Long detailTypeId;
    private Float detailCost;
}
