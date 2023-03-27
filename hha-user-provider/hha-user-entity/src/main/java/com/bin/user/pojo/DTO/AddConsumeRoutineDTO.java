package com.bin.user.pojo.DTO;

import com.bin.user.pojo.PO.RoutineDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/3/27 21:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddConsumeRoutineDTO implements Serializable {
    private String routineName;
    private String routineIcon;
    private List<RoutineDetail> routineDetail;
}
