package com.ruoyi.quartz.domain;

import lombok.Data;

/**
 * @author wjp
 * @date 2022/8/2 15:15
 * @description
 */
@Data
public class ReadResult {

    private Long resultId;

    private Integer slaveId;

    private Integer address;

    private Integer value;
}
