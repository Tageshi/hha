package com.bin.user.pojo.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/28 17:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPeriod implements Serializable {
    Integer number;
    String unit;
}
