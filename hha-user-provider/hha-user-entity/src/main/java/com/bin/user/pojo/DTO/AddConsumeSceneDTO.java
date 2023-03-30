package com.bin.user.pojo.DTO;

import com.bin.user.pojo.PO.SceneItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tageshi
 * @date 2023/3/29 18:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddConsumeSceneDTO implements Serializable {
    private String sceneName;
    private String sceneIcon;
    private SceneItem sceneItem;
}
