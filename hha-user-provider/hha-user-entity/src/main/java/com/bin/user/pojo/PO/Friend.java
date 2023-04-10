package com.bin.user.pojo.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/3 0:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend implements Serializable {
    private Long userId;
}
