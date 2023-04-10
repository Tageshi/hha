package com.bin.user.pojo.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/1 1:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMember implements Serializable {
    private Long userId;
}
