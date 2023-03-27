package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/28 0:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineItemVO implements Serializable {
    private Long routineDetailId;
    private String routineDetailName;
    private Long routineDetailTypeId;
    private String routineDetailTypeName;
    private String routineDetailTypeIcon;
    private Float routineDetailCost;
}
