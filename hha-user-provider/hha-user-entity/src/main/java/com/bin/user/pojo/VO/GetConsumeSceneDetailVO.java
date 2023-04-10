package com.bin.user.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/6 19:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetConsumeSceneDetailVO implements Serializable {
    private String sceneName;
    private List<SceneDetailVO> details;
}
