package com.bin.user.pojo.DTO;

import com.bin.user.pojo.PO.MyPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/28 16:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoutinedOutcomeDTO implements Serializable {
    private String startTime;
    private String endTime;
    private MyPeriod period;
}
