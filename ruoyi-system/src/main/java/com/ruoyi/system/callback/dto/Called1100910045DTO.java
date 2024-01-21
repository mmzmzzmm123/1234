package com.ruoyi.system.callback.dto;

import lombok.Data;

@Data
public class Called1100910045DTO {
/**
 * \"data\":{\"reason\":\"USER_DEACTIVATED_BAN\",
 * \"ban_code\":null,\"user_id\":\"6957015500\",
 * \"robot_serial_no\":\"BCA10B3867E97860390D8AE739C94200\",\"ban_time\":\"2024-01-20T14:01:00.0484591\"}
 */



    /**
     *
     */
    private String user_id;
    /**
     * 机器人编号
     */
    private String robot_serial_no;
    /**
     * code
     */
    private String ban_code;
    /**
     * 封号原因
     */
    private String reason;

    /**
     * 封号时间
     */
    private String ban_time;
}
