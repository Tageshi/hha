package com.bin.user.pojo.PO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * (TypeInfo)表实体类
 *
 * @author tageshi
 * @since 2023-03-26 20:51:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeInfo implements Serializable {
    
    private Long typeId;
    
    private String typeName;
    
    private String typeIcon;
    
    private Date createdTime;
    
    private Integer isDeleted;

}

