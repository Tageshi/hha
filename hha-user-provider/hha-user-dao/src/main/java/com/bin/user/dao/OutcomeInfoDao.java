package com.bin.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.user.pojo.PO.OutcomeInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author tageshi
 * @date 2023/3/17 13:41
 */
public interface OutcomeInfoDao extends BaseMapper<OutcomeInfo> {
    Integer insertOutcomeRecord(@Param("typeId")Long typeId,
                                @Param("outcomeName")String outcomeName,
                                @Param("outcome")Float outcome,
                                @Param("userId")Long userId);
}
