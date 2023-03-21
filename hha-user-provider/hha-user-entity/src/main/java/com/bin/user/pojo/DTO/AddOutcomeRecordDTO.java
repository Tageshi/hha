package com.bin.user.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/16 17:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOutcomeRecordDTO implements Serializable {
    private Long typeId;
    private String outcomeName;
    private float outcome;
}
