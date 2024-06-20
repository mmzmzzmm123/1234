package com.ruoyi.system.mapper.extra;

import org.apache.ibatis.annotations.Param;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-20 16:40:41
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-20     张李鑫                     1.0         1.0 Version
 */
public interface BusOrderAssignmentsExtraMapper {
    int setUserIdAndUpdateStatusById(@Param("userId") Long userId, @Param("newStatus")int newStatus, @Param("oldStatus")int oldStatus,@Param("assignmentId") Long assignmentId);
}
