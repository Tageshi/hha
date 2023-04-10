package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/4/6 19:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetConsumeSceneVO implements Serializable {
    private Long sceneId;
    private String sceneName;
    private String sceneIcon;
}
