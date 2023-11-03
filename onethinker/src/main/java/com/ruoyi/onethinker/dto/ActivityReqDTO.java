package com.ruoyi.onethinker.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 14:09
 * 活动DTO
 */
@Data
public class ActivityReqDTO {

    /** 权重 */
    private Long weight;

    /** 活动开始时间 */
    private Long startTime;

    /** 活动结束时间 */
    private Long endTime;

    /** 活动标题 */
    private String title;

    /** 活动背景图 */
    private String bgUrl;

    /** 批次号 */
    private String batchNo;

    /** 活动类型id */
    private Integer activityType;

    /** 活动明细-红包 **/
    private RedEnvelopeCtrlDTO redEnvelopeCtrlDTO;
}
