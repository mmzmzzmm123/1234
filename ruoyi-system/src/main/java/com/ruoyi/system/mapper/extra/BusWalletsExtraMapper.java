package com.ruoyi.system.mapper.extra;

import com.ruoyi.system.domain.BusWallets;
import com.ruoyi.system.mapper.BusWalletsMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-18 17:05:27
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-18     张李鑫                     1.0         1.0 Version
 */
public interface BusWalletsExtraMapper extends BusWalletsMapper {
    int deduction(@Param("wallet") BusWallets wallet, @Param("amount") Long amount);

    List<BusWallets>  selectBusWalletsByUserIdForUpdate(@Param("userId")Long userId);
}
