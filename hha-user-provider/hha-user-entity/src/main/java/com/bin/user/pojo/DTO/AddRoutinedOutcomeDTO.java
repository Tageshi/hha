package com.bin.user.pojo.DTO;

import com.bin.user.pojo.PO.MyPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/3/28 16:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoutinedOutcomeDTO implements Serializable {
    private Date startTime;
    private Date endTime;
    private MyPeriod period;
}
