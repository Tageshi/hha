package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/6 19:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SceneDetailVO implements Serializable {
    private Long detailId;
    private String detailName;
    private Long typeId;
    private String typeName;
    private String typeIcon;
}
