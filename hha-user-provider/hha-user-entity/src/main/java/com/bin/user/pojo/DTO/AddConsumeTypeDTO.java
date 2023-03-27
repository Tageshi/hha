package com.bin.user.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/26 20:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddConsumeTypeDTO implements Serializable {
    private String typeName;
    private String typeIcon;
}
