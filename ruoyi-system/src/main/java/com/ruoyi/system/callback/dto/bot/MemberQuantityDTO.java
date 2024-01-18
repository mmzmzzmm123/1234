package com.ruoyi.system.callback.dto.bot;

import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/18/018 10:12
 */
@Data
public class MemberQuantityDTO {

    private String chatId;

    private Integer memberQuantity;

    private Integer joinQuantity;

    private Integer inviteJoinQuantity;

    private Integer selfJoinQuantity;

    private Integer leaveQuantity;
}
