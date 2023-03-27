package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/28 0:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineInfoVO implements Serializable {
    private Long routineId;
    private String routineName;
    private String routineIcon;
}
