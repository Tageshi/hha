package com.bin.user.pojo.PO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tageshi
 * @date 2023/4/4 0:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlipayBillItem implements Serializable {
    private String type; // 收/支
    private String desc; // 商品说明
    private float amount; // 金额
    private Long typeId; // 交易分类
    private Date date; // 交易时间
}
