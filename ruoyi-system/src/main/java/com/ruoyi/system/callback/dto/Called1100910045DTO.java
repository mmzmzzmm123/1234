package com.ruoyi.system.callback.dto;

import lombok.Data;

@Data
public class Called1100910045DTO {
    /**
     * {
     * "user_id":"6477024811",
     * "robot_serial_no":"FEA32324DDD647DD1A274CA78D5C0C2C",
     * "ban_code":"",
     * "reason":"PHONE_NUMBER_BANNED"
     * }
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


    /***
     * 操作码
     */
    private String optSerNo;
}
