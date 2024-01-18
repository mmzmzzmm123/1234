package com.ruoyi.system.callback.dto.bot;

import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/18/018 17:18
 */
@Data
public class GroupUpgradeDTO {

    private Long botId;

    private Long oldGroupId;

    private Long newGroupId;

}
