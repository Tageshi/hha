package com.bin.user.pojo.VO;

import com.bin.user.pojo.PO.CostDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/1 22:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyExpensesVO implements Serializable {
    private float totalCost;
    private List<CostDetail> costDetail;
}
