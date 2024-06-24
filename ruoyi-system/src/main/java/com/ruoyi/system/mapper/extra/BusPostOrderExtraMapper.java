package com.ruoyi.system.mapper.extra;

import com.ruoyi.system.domain.BusPostOrder;
import com.ruoyi.system.mapper.BusPostOrderMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-19 10:14:14
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-19     张李鑫                     1.0         1.0 Version
 */
public interface BusPostOrderExtraMapper extends BusPostOrderMapper {
    List<BusPostOrder> findOrderListByUserId(@Param("startStatus") Integer startStatus,@Param("endStatus") Integer endStatus, @Param("merchantId") Long merchantId);

    List<BusPostOrder> findSampleOrder(@Param("busPostOrder")BusPostOrder busPostOrder, @Param("statusList")List<Integer> statusList, @Param("date") Date date,@Param("ids")List<Long>ids);

    List<BusPostOrder> cursorList(@Param("lastId") Long lastId,@Param("limit") Integer limit,@Param("status") Integer status);
}
