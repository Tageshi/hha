package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (SceneInfo)表实体类
 *
 * @author tageshi
 * @since 2023-03-30 21:28:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SceneInfo implements Serializable {
    
    private Long sceneId;
    
    private String sceneName;
    
    private Long userId;
    
    private Date createdTime;
    
    private String isDeleted;

}

