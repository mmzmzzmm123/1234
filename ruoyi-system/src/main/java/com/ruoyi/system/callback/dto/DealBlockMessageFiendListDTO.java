package com.ruoyi.system.callback.dto;

import lombok.Data;

@Data
public class DealBlockMessageFiendListDTO {

    /**
     * 好友编号
     */
    private String friend_serial_no;

    /**
     * 状态：10 疑似被拉黑 20未拉黑 30未查询到好友会话
     */
    private Integer be_pulled_black_status;
}
