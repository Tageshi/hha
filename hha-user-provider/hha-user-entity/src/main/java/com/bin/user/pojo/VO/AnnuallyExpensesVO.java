package com.bin.user.pojo.VO;

import com.bin.user.pojo.PO.CostDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author tageshi
 * @date 2023/4/1 23:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnuallyExpensesVO implements Serializable {
    private float totalCost;
    private List<CostDetail> costDetail;
}
