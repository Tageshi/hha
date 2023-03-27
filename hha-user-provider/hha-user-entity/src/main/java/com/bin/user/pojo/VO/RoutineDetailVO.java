package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/28 0:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDetailVO implements Serializable {
    private String routineName;
    private String routineIcon;
    private List<RoutineItemVO>routineDetail;
}
