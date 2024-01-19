package com.ruoyi.system.callback.dto;

import lombok.Data;

@Data
public class Called1100910003DTO {

    /**
     * {
     *         "offline_code":"10001",
     *         "offline_reason":"收到离线空包消息"
     *     }
     */

    /**
     * 离线码
     */
    private String offline_code;
    /**
     * 离线原因
     */
    private String offline_reason;

    /**
     * 机器人编号
     */
    private String robot_serial_no;


    /***
     * 操作码
     */
    private String optSerNo;
}
